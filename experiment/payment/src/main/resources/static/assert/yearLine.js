const $ = id => document.getElementById(id);
window.onload = () => {
    // 看是否有页面传参
    const queryParam = getQueryParams()["year"];
    if (queryParam !== undefined) {
        // 获取折线图数据
        $("yearH1").innerText = queryParam;
        fetch(`/paymentRecord/yearLine?year=${queryParam}`)
            .then(response => response.json())
            .then(resp => {
                const {moneyOut, moneyIn, money} = resp;
                drawLine(moneyOut, moneyIn, money)
            })
            .catch((error) => console.error('Error:', error));
        // 获取总收入支出数据
        fetch(`/paymentRecord/yearMoney?year=${queryParam}`)
            .then(response => response.json())
            .then(resp => {
                const {moneyOut, moneyIn, money} = resp;
                $('yearMoney').innerHTML = `
        年收入：<span style="color: #17bd17">+${moneyIn}</span>&nbsp&nbsp
        年支出：<span style="color: #c45656">-${moneyOut}</span><br>
        净收入：<span style="color: ${money > 0 ? '#c45656' : '#17bd17'}">${money > 0 ? '+' : ''}${money}</span>`
            })
            .catch((error) => console.error('Error:', error));
    }
}

const drawLine = (moneyOut, moneyIn, money) => {
    const ctx = $('lineChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            datasets: [
                {
                    label: '支出',
                    data: moneyOut,
                    borderColor: 'rgb(102,239,56)',
                    backgroundColor: 'rgba(107,241,17,0.2)',
                    fill: false
                },
                {
                    label: '收入',
                    data: moneyIn,
                    borderColor: 'rgb(239,33,118)',
                    backgroundColor: 'rgba(235,54,81,0.2)',
                    fill: false
                },
                {
                    label: '收支',
                    data: money,
                    borderColor: 'rgb(21,183,218)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    fill: false
                }
            ]
        },
        options: {
            scales: {
                x: {
                    beginAtZero: true
                },
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}