
/*function getTime() {
    $.ajax({
        url:"",
        timeout: 10000,
        success:function(data){
            $("#time").html(data)
        },
        error:function (xhr,type,errorThrown) {
        }
    });

}*/

function get_c1_data(){
    $.ajax({
        url: "covid2022/getData01",
        success:function(data) {
            //console.log(data);
            $("#date .num_date").text(data.data.date);
            $(".num h1").eq(0).text(data.data.currentConfirmedCount);
            $(".num h1").eq(1).text(data.data.confirmedCount);
            $(".num h1").eq(2).text(data.data.suspectedCount);
            $(".num h1").eq(3).text(data.data.curedCount);
            $(".num h1").eq(4).text(data.data.deadCount);
        },
        error: function (xhr, type, errorThrown) {

        }
    });
}


function get_c2_data() {
    $.ajax({
        url: "/covid2022/getData02",
        success: function (response) {

            ec_center2_option.series[0].data=response.data;
            //console.log(response);
            // console.log(ec_center2_option.series[0].data);
            ec_center2.setOption(ec_center2_option);
        },
        error: function (xhr, type, errorThrown) {

        }
    });
}


function get_r2_data() {
    let provinceShortName=[];
    let currentConfirmedCount=[];
    let deadCount=[];
    let curedCount=[];

    $.ajax({
        url: "covid2022/getData06",
        success: function (response) {

            let arr=response.data;
            //console.log(arr);
            for (let i = 0; i < arr.length; i++) {
                provinceShortName.push(arr[i].provinceShortName);
                currentConfirmedCount.push(arr[i].confirmedCount);
                deadCount.push(arr[i].deadCount);
                curedCount.push(arr[i].curedCount);
            }

            ec_right2_option.yAxis.data=provinceShortName;
            ec_right2_option.series[0].data=currentConfirmedCount;
            //ec_right2_option.series[0].data=deadCount;
            ec_right2_option.series[1].data=curedCount;
            console.log(provinceShortName);
            console.log(currentConfirmedCount);
            console.log(deadCount);
            console.log(curedCount);
            ec_right2.setOption(ec_right2_option);
        },
        error: function (xhr, type, errorThrown) {

        }
    });
}

function get_c3_data(){
    $.ajax({
        url: "covid2022/getData07",
        success:function (data) {
            let map=data.data;
            console.log(map);
            $(".txt2 h5").eq(0).text(map.currNB > -1 ? "+" + map.currNB : map.currNB);
            $(".txt2 h5").eq(1).text(map.conNB > -1 ? "+" + map.conNB : map.conNB);
            $(".txt2 h5").eq(2).text(map.suspNB > -1 ? "+" + map.suspNB : map.suspNB);
            $(".txt2 h5").eq(3).text(map.curedNB > -1 ? "+" + map.curedNB : map.curedNB);
            $(".txt2 h5").eq(4).text(map.deadNB > -1 ? "+" + map.deadNB : map.deadNB);
        },
        error: function (xhr, type, errorThrown) {

        }
    });
}



function get_l1_data() {
    $.ajax({
        url: "covid2022/getData04",
        success: function (response) {
            //console.log(response.data);
            ec_left1_option.series[0].data=response.data
            //ec_left1_option.series[3].data=response.data.deadCount;*/

            ec_left1.setOption(ec_left1_option);
        },
        error: function (xhr, type, errorThrown) {

        }
    });
}

function get_l2_data() {
    let data03=[];
    let confirmed=[];
    let suspected=[];
    let cure=[];
    let dead=[];
    $.ajax({
        url: "covid2022/getData03",
        success: function (response) {
            let arr=response.data;
            //console.log(arr);
            for (let i = 0; i < arr.length; i++) {
                data03.push(arr[i].dateId);
                confirmed.push(arr[i].confirmedIncr);
                suspected.push(arr[i].suspectedCountIncr);
                cure.push(arr[i].curedIncr);
                dead.push(arr[i].deadIncr);
            }
           //console.log(data03);
            ec_left2_option.xAxis[0].data=data03;
            ec_left2_option.series[0].data=confirmed;
            ec_left2_option.series[1].data=suspected;
            ec_left2_option.series[2].data=cure;
            ec_left2_option.series[3].data=dead;

            ec_left2.setOption(ec_left2_option);
        },
        error: function (xhr, type, errorThrown) {

        }
    });
}

function get_r1_data() {
    let pror1=[];
    let confr1=[];

    $.ajax({
        url: "/covid2022/getData05",
        success: function (response) {
            let data=response.data;
            for (let i = 0; i < data.length; i++) {
                pror1.push(data[i].name);
                confr1.push(data[i].value);
            }
            ec_right1_option.xAxis[0].data=pror1;
            ec_right1_option.series[0].data=confr1;
            ec_right1.setOption(ec_right1_option);
            //console.log(ec_right1_option.xAxis[0].data[1]);
        },
        error: function (xhr, type, errorThrown) {

        }
    });

}

//setInterval(getTime,1000);
setInterval(get_c1_data,1000);
setInterval(get_c3_data(),1000);

get_l1_data();
get_l2_data();
get_r1_data();
get_r2_data();
get_c2_data();

