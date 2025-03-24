window.onload = () => {
    // 看是否有页面传参
    const queryParam = getQueryParams()["year"];
    if (queryParam !== undefined) {
        document.getElementById("yearH1").innerText = queryParam;
        fetch(`/api/paymentRecord/yearPie?year=${queryParam}`)
            .then(response => response.json())
            .then(resp => {
                const {bigTypes, labels, colors, moneys, outMoney} = resp;
                document.getElementById("outSpan").innerText = `-${outMoney}`;
                drawChart(bigTypes, labels, colors, moneys)
            })
            .catch((error) => console.error('Error:', error));
    }
}

const drawChart = (bigTypes, labels, colors, moneys) => {
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
            },
            onClick: function (event, elements) {
                if (elements.length > 0) {
                    const firstElement = elements[0];
                    const datasetIndex = firstElement.datasetIndex;
                    const index = firstElement.index;
                    const label = this.data.labels[index];
                    const value = this.data.datasets[datasetIndex].data[index];
                    alert(`你点击了 ${label}，钱 ${value} id ${bigTypes[index]}`);
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

