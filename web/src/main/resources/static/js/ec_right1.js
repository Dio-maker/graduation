var ec_right1 = echarts.init(document.querySelector("#r1"), "dark");
    var ec_right1_option = {
        title: {
            text: "江西省确诊TOP5地区",
            textStyle: {
                color: "white",
            },
            left: "left"
        },
        showBackground: true,
        itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#83bff6' },
                { offset: 0.5, color: '#188df0' },
                { offset: 1, color: '#188df0' }
            ])
        },
        emphasis: {
            itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: '#2378f7' },
                    { offset: 0.7, color: '#2378f7' },
                    { offset: 1, color: '#83bff6' }
                ])
            }
        },
        tooltip: {
            trigger: "axis",
            axisPointer: {
                type: "shadow"
            }
        },
        xAxis: [{
            data: [],
            type: 'category',
        }],
        yAxis: {
            type: "value"
        },
        series: [{
            data: [525, 490, 391, 366, 327],
            type: 'bar',
            barMaxWidth: "50%"
        }]
    };
window.addEventListener('resize',function (){
    ec_right1.resize();
})
    ec_right1.setOption(ec_right1_option);
