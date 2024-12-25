window.onload = () => {
    const dateH1 = document.getElementById("dateH1");
    // 看是否有页面传参
    const queryParam = getQueryParams()["month"];
    if (queryParam !== undefined) {
        dateH1.innerText = queryParam
        const dateStr = queryParam.split("-");
        fetch(`/paymentRecord/pieChart?year=${dateStr[0]}&month=${dateStr[1]}`)
            .then(response => response.json())
            .then(resp => {
                const {labels, colors, moneys} = resp;
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
                label: 'pay',
                data: moneys,
                backgroundColor: colors,
                borderColor: colors,
                borderWidth: 1
            }]
        }
    });
}

