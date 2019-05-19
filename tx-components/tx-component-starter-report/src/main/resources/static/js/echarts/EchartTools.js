/**
 * 使用于 V2.0+
 * @type {{ChartConfig: Function, ChartDataFormat: {FormateNOGroupData: Function, FormateGroupData: Function}, ChartOptionTemplates: {CommonOption: {tooltip: {trigger: string}, toolbox: {show: boolean, orient: string, x: string, y: string, color: string[], backgroundColor: string, borderColor: string, borderWidth: number, padding: number, showTitle: boolean, feature: {mark: boolean, restore: {show: boolean}, saveAsImage: {show: boolean}}}}, CommonLineOption: {tooltip: {trigger: string}, toolbox: {x: string, show: boolean, feature: {magicType: {type: string[]}, restore: {show: boolean}, saveAsImage: {show: boolean}}, legend: {x: string, orient: string}}}, Pie: Function, Lines: Function, Bars: Function, Maps: Function}, Charts: {RenderChart: Function, RenderMap: Function}}}
 */

var ECharts = {
    ChartConfig: function (container, option) {
        this.option = {chart: {}, option: option, container: container};
        return this.option;
    },//加载Echarts配置文件
    ChartDataFormat: {
        FormatNOGroupData: function (data) {//data的格式如上的Result1，这种格式的数据，多用于饼图、单一的柱形图的数据源
            var categories = [];
            var datas = [];
            for (var i = 0; i < data.length; i++) {
                categories.push(data[i].name || "");
                datas.push({name: data[i].name, value: data[i].value || 0});
            }
            return {category: categories, data: datas};
        },

        FormatGroupData: function (data, type, is_stack) {//data的格式如上的Result2，type为要渲染的图表类型：可以为line，bar，is_stack表示为是否是堆积图，这种格式的数据多用于展示多条折线图、分组的柱图
            var chart_type = 'line';
            if (type) {
                chart_type = type || 'line';
            }
            var xAxis = [];
            var group = [];
            var series = [];
            var maxValue = 0;
            for (var i = 0; i < data.length; i++) {
                for (var j = 0; j < xAxis.length && xAxis[j] != data[i].name; j++);

                if (j == xAxis.length) {
                    xAxis.push(data[i].name);
                }

                for (var k = 0; k < group.length && group[k] != data[i].group; k++);
                if (k == group.length) {
                    group.push(data[i].group);
                }
            }
            for (var i = 0; i < group.length; i++) {
                var temp = [];
                for (var j = 0; j < data.length; j++) {
                    if (data[j].value > maxValue) {
                        maxValue = data[j].value;
                    }
                    if (group[i] == data[j].group) {

                        if (type == "map") {
                            temp.push({name: data[j].name, value: data[j].value});
                        }
                        else {
                            temp.push(data[j].value);
                        }
                    }
                }

                switch (type) {
                    case 'bar':
                        var series_temp = {name: group[i], data: temp, type: chart_type};
                        if (is_stack) {
                            series_temp = $.extend({}, {stack: 'stack'}, series_temp);
                        }
                        break;
                    case 'map':
                        var series_temp = {
                            name: group[i], type: chart_type, mapType: 'china', selectedMode: 'single',
                            itemStyle: {
                                normal: {label: {show: true}},
                                emphasis: {label: {show: true}}
                            },
                            data: temp
                        };
                        break;

                    case 'line':
                        var series_temp = {name: group[i], data: temp, type: chart_type};
                        if (is_stack) {
                            series_temp = $.extend({}, {stack: 'stack'}, series_temp);
                        }
                        break;

                    default:
                        var series_temp = {name: group[i], data: temp, type: chart_type};

                }
                series.push(series_temp);
            }
            return {category: group, xAxis: xAxis, series: series, maxValue: maxValue};
        },//数据格式化
    },//初始化数据
    ChartOptionTemplates: {
        CommonOption: {//通用的图表基本配置
            tooltip: {
                trigger: 'axis'//tooltip触发方式:axis以X轴线触发,item以每一个数据项触发
            },
            toolbox: {
                show: true,
                orient: 'horizontal',// 布局方式，默认为水平布局，可选为：
                // 'horizontal' ¦ 'vertical'
                x: 'left',                // 水平安放位置，默认为全图右对齐，可选为：
                // 'center' ¦ 'left' ¦ 'right'
                // ¦ {number}（x坐标，单位px）
                y: 'bottom',                  // 垂直安放位置，默认为全图顶端，可选为：
                // 'top' ¦ 'bottom' ¦ 'center'
                // ¦ {number}（y坐标，单位px）
                // color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],

                backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
                borderColor: '#ccc',       // 工具箱边框颜色
                borderWidth: 0,            // 工具箱边框线宽，单位px，默认为0（无边框）
                padding: 5,                // 工具箱内边距，单位px，默认各方向内边距为5，
                showTitle: true,
                feature: {
                    // magicType: {show: true, type: ['line', 'bar','pie']},
                    restore: {show: true},
                    saveAsImage: {show: true}

                },
            }
        },

        CommonLineOption: {//通用的图表基本配置
            tooltip: {
                trigger: 'axis'//tooltip触发方式:axis以X轴线触发,item以每一个数据项触发
            },
            toolbox: {
                show: true,
                orient: 'vertical',// 布局方式，默认为水平布局，可选为：
                left: 'right',
                y: 'center',
                // 'horizontal' ¦ 'vertical'
                // x: 'left',                // 水平安放位置，默认为全图右对齐，可选为：
                // 'center' ¦ 'left' ¦ 'right'
                // ¦ {number}（x坐标，单位px）
                // y: 'bottom',                  // 垂直安放位置，默认为全图顶端，可选为：
                // 'top' ¦ 'bottom' ¦ 'center'
                // ¦ {number}（y坐标，单位px）
                // color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],

                backgroundColor: 'rgba(255,0,0,0)', // 工具箱背景颜色
                borderColor: '#ccc',       // 工具箱边框颜色
                borderWidth: 0,            // 工具箱边框线宽，单位px，默认为0（无边框）
                padding: 5,                // 工具箱内边距，单位px，默认各方向内边距为5，
                showTitle: true,
                feature: {
                    // mark: {show: true},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}

                },
            }
        },

        CommonLinesOption: {//通用的折线图表的基本配置
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                x: '30%',
                show: true,
                feature: {
                    /*   dataView: {readOnly: false}, //数据预览*/
                    mark: {show: true},
                    magicType: {type: ['line', 'bar']}, //支持柱形图和折线图的切换
                    restore: {show: true},
                    saveAsImage: {show: true}

                },
                legend: {
                    x: 'right',
                    orient: 'vertical'
                }
            }
        },
        Pie: function (data, name) {//data:数据格式：{name：xxx,value:xxx}...
            var pie_datas = ECharts.ChartDataFormat.FormatNOGroupData(data);
            var option = {
                title: {
                    text: name,
                    show: true
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {c} ({d}/%)',
                    show: true
                },
                legend: {
                    orient: 'vertical',
                    x: 'right',
                    data: pie_datas.category
                },
                series: [
                    {
                        name: name || "",
                        type: 'pie',
                        //  radius: '45%',
                        radius: ['30%', '50%'],
                        center: ['30%', '70%'],
                        startAngle: 0,
                        // roseType:"radius",
                        data: pie_datas.data,
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    formatter: '{b} : {c} ({d}%)',
                                    position: "outer"
                                },
                                labelLine: {
                                    show: true
                                }
                            }
                        },
                        emphasis: {
                            label: {
                                show: true,
                                position: 'center',
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        }


                    }
                ]

            };
            return $.extend({}, ECharts.ChartOptionTemplates.CommonOption, option);
        },

        Line: function (data, name) {//data:数据格式：{name：xxx,value:xxx}...
            var pie_datas = ECharts.ChartDataFormat.FormatNOGroupData(data);
            var option = {
                yAxis: {
                    type: 'value'
                },
                xAxis: {
                    type: 'category',
                    axisTick: {show: false},
                    data: pie_datas.category
                },
                series: [{
                    data: pie_datas.data,
                    type: 'line',

                    label:{
                        normal: {
                            show: true,
                            // position: "insideBottom",
                            distance: "15",
                            align: "center",
                            verticalAlign: "middle",
                            rotate: 0,
                            formatter: '{c}',
                            fontSize: 16,
                            rich: {
                                name: {
                                    textBorderColor: '#000'
                                }
                            }
                        }
                    }
                }]
            };
            return $.extend({}, ECharts.ChartOptionTemplates.CommonLineOption, option);
        },

        Lines: function (data, name, is_stack) { //data:数据格式：{name：xxx,group:xxx,value:xxx}...
            var stackline_datas = ECharts.ChartDataFormat.FormatGroupData(data, 'line', is_stack);
            var option = {
                legend: {
                    data: stackline_datas.category,
                    x: 'right',
                    orient: 'vertical'
                },
                xAxis: [{
                    type: 'category', //X轴均为category，Y轴均为value
                    data: stackline_datas.xAxis,
                    boundaryGap: false//数值轴两端的空白策略
                }],

                yAxis: [{
                    name: name || '',
                    type: 'value',
                    splitArea: {show: true}
                }],
                series: stackline_datas.series
            };
            return $.extend({}, ECharts.ChartOptionTemplates.CommonLinesOption, option);
        },
        Bars: function (data, name, is_stack) {//data:数据格式：{name：xxx,group:xxx,value:xxx}...
            var bars_dates = ECharts.ChartDataFormat.FormatGroupData(data, 'bar', is_stack);
            var option = {
                legend: {data: bars_dates.category},
                xAxis: [{
                    type: 'category',
                    data: bars_dates.xAxis,
                    axisLabel: {
                        show: true,
                        interval: 'auto',
                        rotate: 0,
                        margion: 8
                    }
                }],

                yAxis: [{
                    type: 'value',
                    name: name || '',
                    splitArea: {show: true}
                }],
                series: bars_dates.series
            };
            return $.extend({}, ECharts.ChartOptionTemplates.CommonLinesOption, option);

        },
        Maps: function (data, name, is_stack) {//data:数据格式：{name：xxx,group:xxx,value:xxx}...
            var maps_datas = ECharts.ChartDataFormat.FormatGroupData(data, 'map', is_stack);
            var option = {
                legend: {data: maps_datas.category},
                dataRange: {
                    min: 0,
                    max: maps_datas.maxValue,
                    x: 'left',
                    y: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    x: 'right',
                    y: 'center',
                    feature: {
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: maps_datas.series
            };
            return $.extend({}, ECharts.ChartOptionTemplates.CommonOption, option);
        },

    },//初始化常用的图表类型
    Charts: {
        RenderChart: function (option,theme) {
            /*   var myChart = echarts.init(option.);*/
            if (option.chart && option.chart.dispose)
                option.chart.dispose();

            option.chart = echarts.init(option.container,theme);


            window.onresize = option.chart.resize;

            option.chart.setOption(option.option, true);

        },
        RenderMap: function (option) {
        }//渲染地图
    }//渲染图表
}