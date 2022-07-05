
var ec_right2 = echarts.init(document.querySelector("#r2"), "dark");



var ec_right2_option ={
    title: {
        text: "各省份确诊人数和治愈人数",
        textStyle: {
            color: "white",
        },
        left: "left"
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            // Use axis to trigger tooltip
            type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
        }
    },
    legend: {
        left:"right"
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'value'
    },
    yAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun','s','ww']
    },
    series: [
        {
            name: '确诊人数',
            type: 'bar',
            stack: 'total',
            color: '#ff3030',
            label: {
                show: true
            },
            emphasis: {
                focus: 'series'
            },
            data: [320, 302, 301, 334, 390, 330, 320]
        },
        {
            name: '治愈人数',
            type: 'bar',
            color: 'green',
            stack: 'total',
            label: {
                show: true
            },
            emphasis: {
                focus: 'series'
            },
            data: [120, 132, 101, 134, 90, 230, 210]
        }

        /* {
             name: '确诊人数',
             type: 'bar',
             stack: 'total',
             label: {
                 show: true
             },
             emphasis: {
                 focus: 'series'
             },
             data: [150, 212, 201, 154, 190, 330, 410]
         }*/
    ],
    dataZoom:[
        {
            type: "inside",
            startValue: 0,
            endValue: 7,
            minValueSpan: 2,
            maxValueSpan: 6,
            yAxisIndex: [0],
            zoomOnMouseWheel: false,  // 关闭滚轮缩放
            moveOnMouseWheel: true, // 开启滚轮平移
            moveOnMouseMove: true  // 鼠标移动能触发数据窗口平移
        },
        {
            type: 'slider',
            realtime: true,
            startValue: 0,
            endValue: 2,
            width:  '3.5',
            height:  '50%',
            yAxisIndex: [0], // 控制y轴滚动
            fillerColor: "rgba(154, 181, 215, 1)", // 滚动条颜色
            borderColor: "rgba(17, 100, 210, 0.12)",
            backgroundColor: '#cfcfcf',//两边未选中的滑动条区域的颜色
            handleSize:0, // 两边手柄尺寸
            showDataShadow: false,//是否显示数据阴影 默认auto
            showDetail: false, // 拖拽时是否展示滚动条两侧的文字
            top:'1%',
            right:'5',
        }

    ]


};

// Enable data zoom when user click bar.
    ec_right2.setOption(ec_right2_option);
