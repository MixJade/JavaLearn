window.onload = () => {
    // 看是否有页面传参
    const queryParam = getQueryParams()["year"];
    if (queryParam !== undefined) {
        document.getElementById("yearH1").innerText = queryParam;
        fetch(`/paymentRecord/yearPie?year=${queryParam}`)
            .then(response => response.json())
            .then(resp => {
                const {labels, colors, moneys, outMoney} = resp;
                document.getElementById("outSpan").innerText = `-${outMoney}`;
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
                label: '当年支出',
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
                label: '当年支出',
                data: moneys,
                backgroundColor: colors,
                borderColor: colors,
                borderWidth: 1
            }]
        }
    });
}

