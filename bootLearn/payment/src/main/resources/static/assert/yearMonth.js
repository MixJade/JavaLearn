window.onload = () => getYearMonth();

const getYearMonth = () => {
    fetch(`/paymentRecord/yearMonth?year=${year}`)
        .then(response => response.json())
        .then(resp => {
            let monthMap = new Map(resp.map(i => [i.month, i]));
            writeMonthCard(monthMap)
        })
        .catch((error) => console.error('Error:', error));
}

let year = 2024;
const twelveCard = document.getElementById("twelveCard");
const writeMonthCard = (monthMap) => {
    for (let i = 1; i < 13; i++) {
        if (monthMap.has(i)) {
            const {moneyOut, moneyIn, money} = monthMap.get(i);
            twelveCard.innerHTML += `<div>
        <a class="downA" href="/paymentRecord/downInsertSql?year=${year}&month=${i}">导出</a>
        <h2>${i}月</h2>
        <p>收支：<span class="${money > 0 ? 'in' : 'out'}">${money > 0 ? '+' : ''}${money}</span></p>
        <hr>
        <p>支出：<span class="out">-${moneyOut}</span>&nbsp&nbsp&nbsp&nbsp&nbsp收入：<span class="in">+${moneyIn}</span></p>
        <hr>
        <button type="button" onclick="jumpToChart(${i})">饼状图</button>
        <button type="button" onclick="jumpCalendar(${i})">日历图</button>
        <button type="button" onclick="jumpIndex(${i})">收支记录</button></div>`
        } else {
            twelveCard.innerHTML += `<div><h2>${i}月</h2></div>`
        }
    }
}

const yearH1 = document.getElementById("yearH1");
/**
 * 添加或减少年数
 * @param isAdd
 */
const addYear = (isAdd) => {
    if (isAdd) {
        if (year === 2025) {
            alert("最大2025年")
            return;
        }
        year++;
    } else {
        if (year === 2024) {
            alert("最小2024年")
            return;
        }
        year--;
    }
    yearH1.innerText = year + "年";
    twelveCard.innerHTML = ""
    getYearMonth();
}

/**
 * 打开对应月份的收支记录
 * @param month 月份数字，如12
 */
const jumpIndex = (month) => {
    window.location.href = `/payCurl.html?month=${year}-${month}`
}
/**
 * 打开对应月份的天数记录
 */
const jumpCalendar = (month) => {
    window.location.href = `/monthDay.html?month=${year}-${month}`
}
/**
 * 打开对应月份的绘图
 */
const jumpToChart = (month) => {
    window.location.href = `/monthPie.html?month=${year}-${month}`
}
/**
 * 打开一年的折线图
 */
const jumpToYearLine = () => {
    window.location.href = `/yearLine.html?year=${year}`
}
/**
 * 打开一年的饼状图
 */
const jumpToYearPie = () => {
    window.location.href = `/yearPie.html?year=${year}`
}
/**
 * 打开一年的报告
 */
const jumpToYearReport = () => {
    window.location.href = `/yearReport.html?year=${year}`
}