//地图
var ec_center2 = echarts.init(document.querySelector("#c2"), "dark");

    window.dataList = [{
            name: "南海诸岛",
            value: 0
        },
        {
            name: '北京',
            value: 54
        },
        {
            name: '天津',
            value: 13
        },
        {
            name: '上海',
            value: 40
        },
        {
            name: '重庆',
            value: 75
        },
    ];
    var ec_center2_option = {
        tooltip: {
            triggerOn: "click",
            formatter: function(e, t, n) {
                return .5 == e.value ? e.name + "：有疑似病例" : e.seriesName + "<br />" + e.name + "：" + e.value
            }
        },
        length:{
            text: [1,2,3,4,5]
        },
        visualMap: {
            min: 500,
            max: 1500,
            left: 26,
            bottom: 40,
            showLabel: true,
            text: ["高", "低"],
            pieces: [{
                gt: 50000,
                label: "> 50000 人",
                color: "#ee1216"
            }, {
                gte: 1000,
                lte: 50000,
                label: "1000 - 50000 人",
                color: "#ff7e86"
            },{
                gte: 500,
                lte: 1000,
                label: "500 - 1000 人",
                color: "#ffa372"
            }, {
                gte: 250,
                lt: 500,
                label: "250 - 500 人",
                color: "#ffa372"
            }, {
                gt: 0,
                lt: 250,
                label: "0 - 250",
                color: "#ffe5bf"
            }, {
                gt: 0,
                lt: 0,
                label: "0 - 100",
                color: "#ffffff"
            }],
            show: true
        },
        geo: {
            map: "china",
            roam: !1,
            scaleLimit: {
                min: 1,
                max: 2
            },
            zoom: 1.1,
            label: {
                normal: {
                    show: true,
                    fontSize: "12",
                    color: "rgba(0,0,0,0.7)"
                },

            },
            itemStyle: {
                normal: {
                    borderWidth: .5,
                    areaColor: "#009fe8",
                    borderColor: "#ffefd5"
                },
                emphasis: {
                    areaColor: "#fff",
                    borderColor: "#4b0082",
                    borderWidth: .5,
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        },
        series: [{
            name: "累计确诊",
            type: "map",
            geoIndex: 0,
            data: window.dataList
        }]


    };
window.addEventListener('resize',function (){
    ec_center2.resize();
})


    ec_center2.setOption(ec_center2_option);
