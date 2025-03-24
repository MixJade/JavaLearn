const $ = id => document.getElementById(id);
window.onload = () => {
    // 看是否有页面传参
    const queryParam = getQueryParams()["year"];
    if (queryParam !== undefined) {
        // 获取总收入支出数据
        fetch(`/api/paymentRecord/yearMoney?year=${queryParam}`)
            .then(response => response.json())
            .then(resp => {
                const {
                    moneyOut,
                    moneyIn,
                    money,
                    monthAvgMoneyIn,
                    monthAvgMoneyOut,
                    monthAvgMoney,
                    lifeMoney,
                    workRatio,
                    lifeRatio,
                    lifeDayPay
                } = resp;
                $('yearH1').innerText = queryParam;
                // 年度
                $('yearMoney').innerHTML = `
        年收入：<span class="in">+${moneyIn}</span>&nbsp&nbsp
        年支出：<span class="out">-${moneyOut}</span><br>
        净收入：<span class="${money > 0 ? 'in' : 'out'}">${money > 0 ? '+' : ''}${money}</span>`;
                // 月度
                $('monthAvgMoney').innerHTML = `
        月平均收入：<span class="in">+${monthAvgMoneyIn}</span>&nbsp&nbsp
        月平均支出：<span class="out">-${monthAvgMoneyOut}</span><br>
        月平均盈利：<span class="${monthAvgMoney > 0 ? 'in' : 'out'}">${monthAvgMoney > 0 ? '+' : ''}${monthAvgMoney}</span>`;
                // 其它
                $('other').innerHTML = `
                一年的食宿总支出: ${lifeMoney}<br>
                劳动回报比: ${workRatio}<br>
                食宿花费占总消费比例: ${lifeRatio}<br>
                平均每天食宿花费: ${lifeDayPay}<br>
                `
            })
            .catch((error) => console.error('Error:', error));
    }
}