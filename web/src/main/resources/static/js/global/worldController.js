/*function time() {
    $.ajax({
        url: "/time",
        timeout: 10000,
        success: function (data) {
            $("#time").html("数据更新时间 : " + data.toString())
        },
        error: function (xhr, type, errorThrown) {

        }
    });
}*/


function worldFourNumber() {
    $.ajax({
        url: "globalCovid/getGlobal01",
        success: function (data) {

            $(".xianyouquezhen2").eq(1).text(data.data.currentConfirmedCount);
            $(".leijiquezhen2").eq(1).text(data.data.confirmedCount);
            $(".leijizhiyu2").eq(1).text(data.data.curedCount);
            $(".leijisiwang2").eq(1).text(data.data.deadCount);
        },
        error: function (xhr, type, errorThrown) {
            //console.log("xxxxxxxx");
        }
    })
}
function worldFourNumber2() {
    $.ajax({
        url: "globalCovid/getGlobal07",
        success: function (response) {
            let map=response.data;
            $(".xianyouquezhen2").eq(0).text(map.currNB > -1 ? "+" + map.currNB : map.currNB);
            $(".leijiquezhen2").eq(0).text(map.conNB > -1 ? "+" + map.conNB : map.conNB);
            $(".leijizhiyu2").eq(0).text(map.curedNB > -1 ? "+" + map.curedNB : map.curedNB);
            $(".leijisiwang2").eq(0).text(map.deadNB > -1 ? "+" + map.deadNB : map.deadNB);
        },
        error: function (xhr, type, errorThrown) {
            //console.log("xxxxxxxx");
        }
    })
}
function worldMapNoChina() {
    let provinceName=[];
    let currentConfirmedCount=[];
    let confirmedCount=[];
    let curedCount=[];
    let deadCount=[];
    let currentConfirmedCount2 = [];
    let confirmedCount2=[];
    let curedCount2=[];
    let deadCount2=[];




    $.ajax({
        url: "globalCovid/getGlobal02",
        success: function (response) {
            let data=response.data;
            //console.log("zzzzzzz");
            for (let i = 0; i < data.length; i++) {
                provinceName.push(data[i].provinceName);
                currentConfirmedCount.push(data[i].currentConfirmedCount);
                confirmedCount.push(data[i].confirmedCount);
                curedCount.push(data[i].curedCount);
                deadCount.push(data[i].deadCount);
            }
            /*console.log(provinceName);
            console.log(currentConfirmedCount);
            console.log(confirmedCount);
            console.log(curedCount);
            console.log(deadCount);*/

            for ( let i = 0; i < provinceName.length; i++) {
                let param = {};
                param.name = provinceName[i];
                param.value = currentConfirmedCount[i];
                currentConfirmedCount2.push(param);
            }
            for ( let i = 0; i < provinceName.length; i++) {
                let param = {};
                param.name = provinceName[i];
                param.value = confirmedCount[i];
                confirmedCount2.push(param);
            }
            for ( let i = 0; i < provinceName.length; i++) {
                let param = {};
                param.name = provinceName[i];
                param.value= curedCount[i];
                curedCount2.push(param);
            }
            for ( let i = 0; i < provinceName.length; i++) {
                let param = {};
                param.name = provinceName[i];
                param.value= deadCount[i];
                deadCount2.push(param);
            }

            /*console.log(provinceName);
            console.log(currentConfirmedCount2);
            console.log(confirmedCount2);
            console.log(curedCount2);
            console.log(deadCount2);*/


            ec_worldMapNoChina_Option.series[0].data = currentConfirmedCount2;
            ec_worldMapNoChina_Option.series[1].data = confirmedCount2;
            ec_worldMapNoChina_Option.series[2].data = curedCount2;
            ec_worldMapNoChina_Option.series[3].data = deadCount2;
            ec_worldMapNoChina.setOption(ec_worldMapNoChina_Option);
        },
        error: function (xhr, type, errorThrown) {

        }
    })
}





function foreignCumulativeDiagnosisTop10Countries() {
    let provinceName=[];
    let currentConfirmedCount=[];
    let confirmedCount=[];
    let curedCount=[];
    let deadCount=[];
    $.ajax({
        url: "globalCovid/getGlobal03",
        success: function (response) {
            let data=response.data;
            //console.log(data);
            for (let i = 0; i < data.length; i++) {
                provinceName.push(data[i].provinceName);
                currentConfirmedCount.push(data[i].currentConfirmedCount);
                confirmedCount.push(data[i].confirmedCount);
                curedCount.push(data[i].curedCount);
                deadCount.push(data[i].deadCount);
            }
            ec_foreignCumulativeDiagnosisTop10Countries_Option.yAxis[0].data = provinceName
            ec_foreignCumulativeDiagnosisTop10Countries_Option.series[0].data = currentConfirmedCount
            ec_foreignCumulativeDiagnosisTop10Countries_Option.series[1].data = confirmedCount
            ec_foreignCumulativeDiagnosisTop10Countries_Option.series[2].data = curedCount
            ec_foreignCumulativeDiagnosisTop10Countries_Option.series[3].data = deadCount
            ec_foreignCumulativeDiagnosisTop10Countries.setOption(ec_foreignCumulativeDiagnosisTop10Countries_Option)
        },
        error: function (xhr, type, errorThrown) {

        }
    })
}

function overseasCountriesWithMoreThan10000HaveBeenConfirmedCases() {
    let provinceName=[];
    let currentConfirmedCount=[];
    let currentConfirmedCount2=[];
    $.ajax({
        url: "globalCovid/getGlobal04",
        success: function (response) {
            let data=response.data;
            for (let i = 0; i < data.length; i++) {
                provinceName.push(data[i].provinceName);
                currentConfirmedCount.push(data[i].currentConfirmedCount);
            }
            for ( let i = 0; i < provinceName.length; i++) {
                let param = {};
                param.name = provinceName[i];
                param.value = currentConfirmedCount[i];
                currentConfirmedCount2.push(param);
            }
            ec_overseasCountriesWithMoreThan10000HaveBeenConfirmedCases_Option.legend.data = provinceName;
            ec_overseasCountriesWithMoreThan10000HaveBeenConfirmedCases_Option.series[0].data = currentConfirmedCount2;
            ec_overseasCountriesWithMoreThan10000HaveBeenConfirmedCases.setOption(ec_overseasCountriesWithMoreThan10000HaveBeenConfirmedCases_Option)
        },
        error: function (xhr, type, errorThrown) {

        }
    })
}

function globalCumulativeTrend() {
    let date=[];
    let currentConfirmedCount=[];
    let curedCount=[];
    let deadCount=[];
    $.ajax({
        url: "globalCovid/getGlobal05",
        success: function (response) {
            let data=response.data;
            //console.log(data);
            for (let i = 0; i < data.length; i++) {
                date.push(data[i].date);
                currentConfirmedCount.push(data[i].currentConfirmedCount);
                curedCount.push(data[i].curedCount);
                deadCount.push(data[i].deadCount);
            }
            ec_globalCumulativeTrend_Option.xAxis[0].data = date;
            ec_globalCumulativeTrend_Option.series[0].data = curedCount;
            ec_globalCumulativeTrend_Option.series[1].data = deadCount;
            ec_globalCumulativeTrend_Option.series[2].data = currentConfirmedCount;
            ec_globalCumulativeTrend.setOption(ec_globalCumulativeTrend_Option)
        },
        error: function (xhr, type, errorThrown) {

        }
    })
}



function theNumberOfForeignCountriesWithConfirmedCases() {
    let continents=[];
    let confirmedCount=[];
    let confirmedCount2=[];
    $.ajax({
        url: "globalCovid/getGlobal06",
        success: function (response) {
            let data=response.data;
            for (let i = 0; i < data.length; i++) {
                continents.push(data[i].continents);
                confirmedCount.push(data[i].confirmedCount);

            }
            console.log(confirmedCount);
            for ( let i = 0; i < continents.length; i++) {
                let param = {};
                param.name = continents[i];
                param.value = confirmedCount[i];
                confirmedCount2.push(param);
            }
            ec_theNumberOfForeignCountriesWithConfirmedCases_Option.legend.data = continents;
            ec_theNumberOfForeignCountriesWithConfirmedCases_Option.series[0].data =confirmedCount2;
            ec_theNumberOfForeignCountriesWithConfirmedCases.setOption(ec_theNumberOfForeignCountriesWithConfirmedCases_Option)
        },
        error: function (xhr, type, errorThrown) {

        }
    })









}






//time()
worldFourNumber()
worldFourNumber2()
foreignCumulativeDiagnosisTop10Countries()
theNumberOfForeignCountriesWithConfirmedCases()

overseasCountriesWithMoreThan10000HaveBeenConfirmedCases()
globalCumulativeTrend()

worldMapNoChina()



setInterval(time, 1000000)
setInterval(worldFourNumber, 10000)
/*setInterval(newCasesInTheTop10CountriesWithin24Hours, 1000000)
setInterval(theTop10CountriesGrewFastestInSevenDays, 1000000)
setInterval(foreignCumulativeDiagnosisTop10Countries, 1000000)
setInterval(theNumberOfForeignCountriesWithConfirmedCases, 1000000)
setInterval(overseasCountriesWithMoreThan10000ConfirmedCases, 1000000)
setInterval(overseasCountriesWithMoreThan10000HaveBeenConfirmedCases, 1000000)
setInterval(globalCumulativeTrend, 1000000)
setInterval(globalCumulativeCureMortality, 1000000)
setInterval(worldMapNoChina, 1000000)*/













