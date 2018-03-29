package com.lfish.lotterysscjh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lfish.lotterysscjh.adapter.HistoryAdapter;
import com.lfish.lotterysscjh.adapter.LineChatPosAdapter;
import com.lfish.lotterysscjh.dao.YYEnter;
import com.lfish.lotterysscjh.net.RetrofitManager;
import com.lfish.lotterysscjh.net.dao.Result;
import com.lfish.lotterysscjh.net.dao.YYResult;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

public class LineChartActivity extends AppCompatActivity {

    Spinner spinner,posSpinner;
    List<YYEnter> yyEnters;
    LineChart lineChart;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartline);
        yyEnters = GoodsResultActivity.getYyEnters();
        spinner = (Spinner) findViewById(R.id.spinner1);
        posSpinner = (Spinner) findViewById(R.id.spinner2);
        lineChart = (LineChart) findViewById(R.id.chart_line);
        pieChart = (PieChart) findViewById(R.id.pieChart);
//        pieChart.setVisibility(View.GONE);

        spinner.setAdapter(new HistoryAdapter(this,yyEnters));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getYYMsg(yyEnters.get(position).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initPieChart();


    }

    private void initPieChart() {

        pieChart.setUsePercentValues(true);
//        pieChart.setDescription("");
        pieChart.setCenterText("50期概率统计");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(30f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(20);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);


        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setEnabled(true);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setForm(Legend.LegendForm.CIRCLE);

        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


    }



    public void getYYMsg(String msgcode){
        showProgressDialog2();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        HashMap<String,String> params = new HashMap<>();
        params.put("code",msgcode);
        params.put("count",50+"");
        params.put("showapi_appid","57692");
        params.put("showapi_test_draft","false");
        params.put("showapi_timestamp",format.format(new Date(System.currentTimeMillis())));
        params.put("showapi_sign","bca648dc5285405987e4b4acb5329a73");

        RetrofitManager.getInstance().getYYMSG("https://route.showapi.com/44-2", params, new Subscriber<Result<YYResult>>() {
            @Override
            public void onCompleted() {
                closeProgressDialog2();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<YYResult> yyResultResult) {
                Log.i("GetEd",yyResultResult.getShowapi_res_code()+"");


//                ArrayList<YYResult.YYRESULT_CHILD> result = yyResultResult.getShowapi_res_body().getResult();

                final YYResult showapi_res_body = yyResultResult.getShowapi_res_body();

//                drawLineChat(showapi_res_body);

                new AsyncTask<Void,Void,Void>(){

                    Integer[][] data = null;
                    ArrayList<Entry> values1 = new ArrayList<>();
                    ArrayList<String> times = new ArrayList<String>();
                    @Override
                    protected Void doInBackground(Void... params) {
                        for(int i = 0;i<showapi_res_body.getResult().size();i++){
                            String[] split = showapi_res_body.getResult().get(i).getOpenCode().split(",|\\+");
                            times.add(showapi_res_body.getResult().get(i).getTime());
                            if(data==null){
                                data = new Integer[split.length][showapi_res_body.getResult().size()];
                                System.out.println(split.length+"?"+showapi_res_body.getResult().size());
                            }


                            for(int j =0;j < split.length;j++){
                                data[j][i] = Integer.parseInt(split[j]);
                            }
                        }

                        Log.i("???--?>????-",values1.size()+"");

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        if(data== null || data.length<1){
                            return;
                        }else {
                            posSpinner.setAdapter(new LineChatPosAdapter(LineChartActivity.this, data.length));
                        }

                        posSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                drawLineChatPos(data,times,position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


//                        drawLineChatPos(data,times,0);
                    }
                }.execute();

            }
        });

    }

    public void drawLineChatPos(Integer[][] data, final ArrayList<String> times,int pos){
        List<Entry> datas = new ArrayList<>();
        HashMap<Integer,Integer> present = new HashMap<>();
        closeProgressDialog2();
        for(int x = 0;x<data[pos].length;x++){
            float x1 = data[pos][x];
            float y1 = x;
            datas.add(new Entry(y1, x1));

            Integer integer = present.get(data[pos][x]);
            if(integer==null){
                integer = 0;
            }
            present.put(data[pos][x],integer+1);

            Log.i("???---",data[pos][x]+"="+(integer+1));

        }

        drawLineChat(data,datas,times);
        drawPieChat(present);
    }

    private void drawPieChat(HashMap<Integer,Integer> present) {

//        ArrayList<String> nameList = new ArrayList<String>();
//        for (int i = 0; i < present.size(); i++) {
//            nameList.add("位" + (i + 1));
//        }
        /**
         * valueList将一个饼形图分成三部分，各个区域的百分比的值
         * Entry构造函数中
         * 第一个值代表所占比例，
         * 第二个值代表区域位置
         * （可以有第三个参数，表示携带的数据object）这里没用到
         */
        ArrayList<PieEntry> valueList = new ArrayList<PieEntry>();

        Iterator<Map.Entry<Integer, Integer>> iterator = present.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Integer> next = iterator.next();
            valueList.add(new PieEntry(next.getValue(), next.getKey()+"概率"));
        }

//        valueList.add(new PieEntry(30, 1));
//        valueList.add(new PieEntry(50, 2));

        //显示在比例图上
        PieDataSet dataSet = new PieDataSet(valueList, "数字出现概率");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(3f);
        // 部分区域被选中时多出的长度
        dataSet.setSelectionShift(5f);

        // 设置饼图各个区域颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        //设置以百分比显示
        data.setValueFormatter(new PercentFormatter());
        //区域文字的大小
        data.setValueTextSize(11f);
        //设置区域文字的颜色
        data.setValueTextColor(Color.WHITE);
        //设置区域文字的字体
        data.setValueTypeface(Typeface.DEFAULT);
        data.setDrawValues(true);

        pieChart.setData(data);

        //设置是否显示区域文字内容
//        pieChart.setDrawSliceText(pieChart.isDrawSliceTextEnabled());
        //设置是否显示区域百分比的值
        for (IDataSet<?> set : pieChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();

    }

    private void drawLineChat(Integer[][] data, final List<Entry> values1, final ArrayList<String> times) {


        Log.i("?","end");

        Description description = new Description();
        description.setText("");
        description.setTextColor(Color.RED);
        description.setTextSize(10);
        lineChart.setDescription(description);//设置图表描述信息
        lineChart.setNoDataText("没有数据");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线


//        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();
//
//        values1.add(new Entry(7, 1));
//        values1.add(new Entry(5, 2));
//        values1.add(new Entry(10, 3));
//        values1.add(new Entry(2, 4));
//        values1.add(new Entry(9, 5));
//        values1.add(new Entry(7, 6));




//        values1.add(new Entry(07, 0));
//        values1.add(new Entry(02, 1));
//        values1.add(new Entry(02, 2));
//        values1.add(new Entry(4, 6));

//        values2.add(new Entry(3, 2));
//        values2.add(new Entry(4, 6));
//        values2.add(new Entry(9, 130));
//        values2.add(new Entry(12, 85));
//        values2.add(new Entry(15, 90));

        //LineDataSet每一个对象就是一条连接线
        LineDataSet set1;
        LineDataSet set2;

        //判断图表中原来是否有数据
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            //获取数据1
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
//            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
//            set2.setValues(values2);
            //刷新数据
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
            lineChart.animateXY(1000, 1000); // 图3
        } else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(values1,"50期彩票开奖走势");
            set1.setColor(Color.parseColor("#FFA000"));
            set1.setCircleColor(Color.parseColor("#FFA000"));
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(5f);//设置焦点圆心的大小
            set1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setHighLightColor(Color.WHITE);//设置点击交点后显示交高亮线的颜色
            set1.setValueTextSize(9f);//设置显示值的文字大小
            set1.setDrawFilled(false);//设置禁用范围背景填充

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    int i = Float.valueOf(value).intValue();
                    if(i>=values1.size()){
                        return "";
                    }
                    Entry entry1 = values1.get(i);
                    return Float.valueOf(entry1.getX()).intValue()+"";
                }
            });


            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);//设置范围背景填充
            } else {
                set1.setFillColor(Color.BLACK);
            }

            //设置数据2
            set2 = new LineDataSet(values2, "测试数据2");
            set2.setColor(Color.GRAY);
            set2.setCircleColor(Color.GRAY);
            set2.setLineWidth(1f);
            set2.setCircleRadius(7f);
            set2.setValueTextSize(10f);

            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets
//            dataSets.add(set2);
            //创建LineData对象 属于LineChart折线图的数据集合

            LineData dataLine = new LineData(dataSets);


            XAxis xAxis = lineChart.getXAxis();
//            xAxis.setAxisLineWidth(100);
//            xAxis.setAxisMinimum(1);
//            xAxis.setSpaceMin(1);
//            xAxis.setDrawGridLines(false);
            xAxis.setLabelRotationAngle(30);
            xAxis.setSpaceMax(2);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    int i = Float.valueOf(value).intValue();

                    if(i>=times.size()){
                        return "";
                    }

                    return times.get(i);
                }
            });

            YAxis axisLeft = lineChart.getAxisLeft();
            axisLeft.setMinWidth(1f);
//            axisLeft.setValueFormatter(new IAxisValueFormatter() {
//                @Override
//                public String getFormattedValue(float value, AxisBase axis) {
//                    return "2018-1-4";
//                }
//            });

            // 添加到图表中
            lineChart.setData(dataLine);

            //绘制图表
//            lineChart.invalidate();

            lineChart.animateXY(1000, 1000); // 图3

        }
    }

    private ProgressDialog mDialog2;
    private void showProgressDialog2(){
        if(mDialog2==null){
            mDialog2 = new ProgressDialog(LineChartActivity.this);
            mDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mDialog2.setMessage("正在加载 ，请等待...");
            mDialog2.setIndeterminate(false);//设置进度条是否为不明确
            mDialog2.setCancelable(true);//设置进度条是否可以按退回键取消
            mDialog2.setCanceledOnTouchOutside(false);
            mDialog2.setProgress(10);
            mDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    mDialog2=null;
                }
            });
            mDialog2.show();

        }
    }
    private void closeProgressDialog2(){
        if(mDialog2!=null){
            mDialog2.dismiss();
            mDialog2=null;
        }
    }
}
