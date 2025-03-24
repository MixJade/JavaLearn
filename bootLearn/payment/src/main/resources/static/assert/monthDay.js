const $ = id => document.getElementById(id);
window.onload = () => {
    // 看是否有页面传参
    const queryParam = getQueryParams()["month"];
    if (queryParam !== undefined) {
        const dateStr = queryParam.split("-"); // 传参类似于 2024-1
        // 初始化
        init(dateStr[0], dateStr[1]);
    }
}

let year, month

const init = (year, month) => {
    setDate(year, month);
    listenEvents();
}

const listenEvents = () => {
    // dom
    const lastMonthButton = $('lastMonth');
    const nextMonthButton = $('nextMonth');
    // last month
    lastMonthButton.addEventListener('click', () => {
        if (month === 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        setDate(year, month);
    });
    // next month
    nextMonthButton.addEventListener('click', () => {
        if (month === 12) {
            month = 1;
            year++;
        } else {
            month++
        }
        setDate(year, month);
    });
}

const setDate = (yearVar, monthVar) => {
    year = yearVar;
    month = monthVar;
    // the only place to do renders
    renderCurrentDate();
    fetch(`/api/paymentRecord/monthDay?year=${yearVar}&month=${monthVar}`)
        .then(response => response.json())
        .then(resp => renderDates(resp))
        .catch((error) => console.error('Error:', error));
}

const renderCurrentDate = () => {
    const currentDateEL = $('currentDate');
    currentDateEL.textContent = `${year}-${month}`;
}

const getLastMonthInfo = () => {
    let lastMonth = month - 1;
    let yearOfLastMonth = year;
    if (lastMonth === 0) {
        lastMonth = 12;
        yearOfLastMonth -= 1;
    }
    let dayCountInLastMonth = getDayCount(yearOfLastMonth, lastMonth);

    return {
        lastMonth,
        yearOfLastMonth,
        dayCountInLastMonth
    }
}

const getNextMonthInfo = () => {
    let nextMonth = month + 1;
    let yearOfNextMonth = year;
    if (nextMonth === 13) {
        nextMonth = 1;
        yearOfNextMonth += 1;
    }
    let dayCountInNextMonth = getDayCount(yearOfNextMonth, nextMonth);

    return {
        nextMonth,
        yearOfNextMonth,
        dayCountInNextMonth
    }
}

const renderDates = dateList => {
    // 将dateList转为map
    let dateMap = new Map(dateList.map(i => [i.payDate, i]));
    // DOM
    const datesEL = $('dates');
    datesEL.innerHTML = '';
    const dayCountInCurrentMonth = getDayCount(year, month);
    const firstDay = getDayOfFirstDate();
    const {lastMonth, yearOfLastMonth, dayCountInLastMonth} = getLastMonthInfo();
    const {nextMonth, yearOfNextMonth} = getNextMonthInfo();

    for (let i = 1; i <= 42; i++) {
        const dateEL = document.createElement('div');
        dateEL.classList.add('date');
        let dateString;
        let date;
        let isThisMonth = false;
        if (firstDay > 1 && i < firstDay) {
            // dates in last month
            date = dayCountInLastMonth - (firstDay - i) + 1;
            dateString = `${yearOfLastMonth}-${lastMonth}`;
        } else if (i >= dayCountInCurrentMonth + firstDay) {
            // dates in next month
            date = i - (dayCountInCurrentMonth + firstDay) + 1;
            dateString = `${yearOfNextMonth}-${nextMonth}`;
        } else {
            // dates in current month
            date = i - firstDay + 1;
            dateString = `${year}-${String(month).padStart(2, '0')}-${String(date).padStart(2, '0')}`;
            dateEL.classList.add('currentMonth');
            isThisMonth = true;
        }
        dateEL.textContent = date;
        dateEL.title = dateString;
        // 满足某个条件后，插入记录
        if (isThisMonth && dateMap.has(dateString)) {
            const {payCount, moneyOut, moneyIn} = dateMap.get(dateString);
            dateEL.innerHTML += `
        <span class="count">${payCount}</span>
        <span class="out">-${moneyOut}</span>`
            if (moneyIn > 0) dateEL.innerHTML += `<span class="in">+${moneyIn}</span>`
            // 还有跳转功能
            dateEL.addEventListener('click', () => jumpToIndex(dateString));
        }
        datesEL.append(dateEL);
    }
}

const getDayCount = (year, month) => {
    return new Date(year, month, 0).getDate();
}

const getDayOfFirstDate = () => {
    let day = new Date(year, month - 1, 1).getDay();
    return day === 0 ? 7 : day;
}

const jumpToIndex = (date) => {
    window.location.href = `/payCurl.html?month=${date}`
}