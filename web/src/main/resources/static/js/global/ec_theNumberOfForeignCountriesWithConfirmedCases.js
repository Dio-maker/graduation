var ec_theNumberOfForeignCountriesWithConfirmedCases = echarts.init(document.getElementById('theNumberOfForeignCountriesWithConfirmedCases'), "infographic");
var ec_theNumberOfForeignCountriesWithConfirmedCases_Option = {
    title: {
        text: "海外现有确诊病例的各洲国家数量",
        left: 'left'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
        left: 'center',
        top: 'bottom',
        data: [
            'rose1',
            'rose2',
            'rose3',
            'rose4',
            'rose5',
            'rose6',
            'rose7',
            'rose8'
        ]
    },
    toolbox: {
        show: true,
        feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
        }
    },
    series: [
        {
            name: '南丁格尔',
            type: 'pie',
            radius: [0, 160],
            center: ['50%', '45%'],
            roseType: 'radius',
            itemStyle: {
                borderRadius: 5
            },
            label: {
                show: false
            },
            emphasis: {
                label: {
                    show: true
                }
            },
            data: [
                { value: 40, name: 'rose 1' },
                { value: 33, name: 'rose 2' },
                { value: 28, name: 'rose 3' },
                { value: 22, name: 'rose 4' },
                { value: 20, name: 'rose 5' },
                { value: 15, name: 'rose 6' },
                { value: 12, name: 'rose 7' },
                { value: 10, name: 'rose 8' }
            ]
        }]
};
ec_theNumberOfForeignCountriesWithConfirmedCases.setOption(ec_theNumberOfForeignCountriesWithConfirmedCases_Option)
