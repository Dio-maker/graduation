var ec_left1 = echarts.init(document.querySelector("#l1"), "dark");

    var ec_left1_option =  {
        title: {
            text: '境外输入分布',
            left: 0,
            top:0
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a}</br>{b} : {c}人 ({d}%)'
        },


        series: [
            {
                name: '境外输入',
                type: 'pie',
                radius: [60, 120],
                center: ['50%', '50%'],

                itemStyle: {
                    borderRadius: 5
                },
                data: [

                    { value: 28, name: 'rose ' },
                    { value: 26, name: 'rose 3' },
                    { value: 24, name: 'rose 4' },
                    { value: 22, name: 'rose 5' },
                    { value: 20, name: 'rose 6' },
                    { value: 18, name: 'rose 7' },
                    { value: 16, name: 'rose 8' }
                ]
            }
        ]
    };
window.addEventListener('resize',function (){
    ec_left1.resize();
})
    ec_left1.setOption(ec_left1_option);
