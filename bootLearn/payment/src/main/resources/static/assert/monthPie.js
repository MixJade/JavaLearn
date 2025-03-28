window.onload = () => {
    const dateH1 = document.getElementById("dateH1");
    const outSpan = document.getElementById("outSpan");
    // 看是否有页面传参
    const queryParam = getQueryParams()["month"];
    if (queryParam !== undefined) {
        dateH1.innerText = queryParam
        const dateStr = queryParam.split("-");
        fetch(`/api/paymentRecord/monthPie?year=${dateStr[0]}&month=${dateStr[1]}`)
            .then(response => response.json())
            .then(resp => {
                const {labels, colors, moneys, outMoney} = resp;
                outSpan.innerText = `-${outMoney}`
                drawChart(labels, colors, moneys)
            })
            .catch((error) => console.error('Error:', error));
    }
}

const drawChart = (labels, colors, moneys) => {
    // 柱状图
    const ctxBar = document.getElementById('barChart').getContext('2d');
    new Chart(ctxBar, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '支出',
                data: moneys,
                backgroundColor: colors,
                borderColor: colors,
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // 饼状图
    const ctxPie = document.getElementById('pieChart').getContext('2d');
    new Chart(ctxPie, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: '支出',
                data: moneys,
                backgroundColor: colors,
                borderColor: colors,
                borderWidth: 1
            }]
        }
    });
}

