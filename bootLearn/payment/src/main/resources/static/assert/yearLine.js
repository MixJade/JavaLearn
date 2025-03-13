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
        // 获取每月收支类型数据
        fetch(`/paymentRecord/yearTypeLLine?year=${queryParam}`)
            .then(response => response.json())
            .then(resp => {
                const {eat, run, home, play, life, buy, salary} = resp;
                drawLine2(eat, run, home, play, life, buy, salary)
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
                    label: '盈余',
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
const drawLine2 = (eat, run, home, play, life, buy, salary) => {
    const ctx = $('lineChart2').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            datasets: [
                {
                    label: '餐饮',
                    data: eat,
                    borderColor: 'rgb(255, 69, 0)',
                    backgroundColor: 'rgb(255, 69, 0)',
                    fill: false
                },
                {
                    label: '出行',
                    data: run,
                    borderColor: 'rgb(30, 144, 255)',
                    backgroundColor: 'rgb(30, 144, 255)',
                    fill: false
                },
                {
                    label: '居住',
                    data: home,
                    borderColor: 'rgb(107, 142, 35)',
                    backgroundColor: 'rgb(107, 142, 35)',
                    fill: false
                },
                {
                    label: '娱乐',
                    data: play,
                    borderColor: 'rgb(255, 20, 147)',
                    backgroundColor: 'rgb(255, 20, 147)',
                    fill: false
                },
                {
                    label: '日常',
                    data: life,
                    borderColor: 'rgb(0, 128, 0)',
                    backgroundColor: 'rgb(0, 128, 0)',
                    fill: false
                },
                {
                    label: '购物',
                    data: buy,
                    borderColor: 'rgb(181, 153, 0)',
                    backgroundColor: 'rgb(181, 153, 0)',
                    fill: false
                },
                {
                    label: '工资',
                    data: salary,
                    borderColor: 'rgb(0, 169, 169)',
                    backgroundColor: 'rgb(0, 169, 169)',
                    fill: false
                },
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