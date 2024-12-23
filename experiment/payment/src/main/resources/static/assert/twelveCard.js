window.onload = () => {
    fetch(`/paymentRecord/month?year=2024`)
        .then(response => response.json())
        .then(resp => {
            let monthMap = new Map(resp.map(i => [i.month, i]));
            writeMonthCard(monthMap)
        })
        .catch((error) => console.error('Error:', error));
}

const twelveCard = document.getElementById("twelveCard");
const writeMonthCard = (monthMap) => {
    for (let i = 1; i < 13; i++) {
        if (monthMap.has(i)) {
            const {moneyOut, moneyIn, money} = monthMap.get(i);
            twelveCard.innerHTML += `<div><h2>${i}月</h2>
        <p>收支：<span class="${money > 0 ? 'in' : 'out'}">${money > 0 ? '+' : ''}${money}</span></p>
        <hr>
        <p>支出：<span class="out">-${moneyOut}</span>&nbsp&nbsp&nbsp&nbsp&nbsp收入：<span class="in">+${moneyIn}</span></p>
        <hr>
        <button type="button">柱状图</button>
        <button type="button">饼状图</button>
        <button type="button">日历图</button>
        <button type="button" onclick="jumpIndex(${i})">收支记录</button></div>`
        } else {
            twelveCard.innerHTML += `<div><h2>${i}月</h2></div>`
        }
    }
}

/**
 * 打开对应月份的收支记录
 * @param month 月份数字，如12
 */
const jumpIndex = (month) => {
    window.location.href = `/index.html?month=2024-${month}`
}