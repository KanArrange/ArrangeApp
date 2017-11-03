package com.chart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chart.app.BubbleChartActivity;
import com.chart.app.ColumnChartActivity;
import com.chart.app.ComboLineColumnChartActivity;
import com.chart.app.GoodBadChartActivity;
import com.chart.app.LineChartActivity;
import com.chart.app.LineColumnDependencyActivity;
import com.chart.app.PieChartActivity;
import com.chart.app.PreviewColumnChartActivity;
import com.chart.app.PreviewLineChartActivity;
import com.chart.app.SpeedChartActivity;
import com.chart.app.TempoChartActivity;
import com.chart.app.ViewPagerChartsActivity;
import com.chart.view.AbstractChartView;
import com.chart.view.LineChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kan212 on 17/11/3.
 * 图标总结的activity
 */

public class ChartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_main);
        if(null == savedInstanceState){
            getSupportFragmentManager().beginTransaction().add(R.id.container,new PlaceholderFragment()).commitAllowingStateLoss();
        }
    }

    public enum ChartType {
        LINE_CHART, COLUMN_CHART, PIE_CHART, BUBBLE_CHART, PREVIEW_LINE_CHART, PREVIEW_COLUMN_CHART, OTHER
    }

    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener{
        private ListView listView;
        private ChartSamplesAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chart_main, container, false);
            listView = (ListView) rootView.findViewById(android.R.id.list);
            adapter = new ChartSamplesAdapter(getActivity(), 0, generateSamplesDescriptions());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            return rootView;
        }
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Intent intent;

            switch (position) {
                case 0:
                    // Line Chart;
                    intent = new Intent(getActivity(), LineChartActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    // Column Chart;
                    intent = new Intent(getActivity(), ColumnChartActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    // Pie Chart;
                    intent = new Intent(getActivity(), PieChartActivity.class);
                    startActivity(intent);
                    break;
                case 3:
                    // Bubble Chart;
                    intent = new Intent(getActivity(), BubbleChartActivity.class);
                    startActivity(intent);
                    break;
                case 4:
                    // Preview Line Chart;
                    intent = new Intent(getActivity(), PreviewLineChartActivity.class);
                    startActivity(intent);
                    break;
                case 5:
                    // Preview Column Chart;
                    intent = new Intent(getActivity(), PreviewColumnChartActivity.class);
                    startActivity(intent);
                    break;
                case 6:
                    // Combo Chart;
                    intent = new Intent(getActivity(), ComboLineColumnChartActivity.class);
                    startActivity(intent);
                    break;
                case 7:
                    // Line Column Dependency;
                    intent = new Intent(getActivity(), LineColumnDependencyActivity.class);
                    startActivity(intent);
                    break;
                case 8:
                    // Tempo line chart;
                    intent = new Intent(getActivity(), TempoChartActivity.class);
                    startActivity(intent);
                    break;
                case 9:
                    // Speed line chart;
                    intent = new Intent(getActivity(), SpeedChartActivity.class);
                    startActivity(intent);
                    break;
                case 10:
                    // Good Bad filled line chart;
                    intent = new Intent(getActivity(), GoodBadChartActivity.class);
                    startActivity(intent);
                    break;
                case 11:
                    // Good Bad filled line chart;
                    intent = new Intent(getActivity(), ViewPagerChartsActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }

        private List<ChartSampleDescription> generateSamplesDescriptions() {
            List<ChartSampleDescription> list = new ArrayList<ChartSampleDescription>();

            list.add(new ChartSampleDescription("Line Chart", "", ChartType.LINE_CHART));
            list.add(new ChartSampleDescription("Column Chart", "", ChartType.COLUMN_CHART));
            list.add(new ChartSampleDescription("Pie Chart", "", ChartType.PIE_CHART));
            list.add(new ChartSampleDescription("Bubble Chart", "", ChartType.BUBBLE_CHART));
            list.add(new ChartSampleDescription("Preview Line Chart",
                    "Control line chart viewport with another line chart.", ChartType.PREVIEW_LINE_CHART));
            list.add(new ChartSampleDescription("Preview Column Chart",
                    "Control column chart viewport with another column chart.", ChartType.PREVIEW_COLUMN_CHART));
            list.add(new ChartSampleDescription("Combo Line/Column Chart", "Combo chart with lines and columns.",
                    ChartType.OTHER));
            list.add(new ChartSampleDescription("Line/Column Chart Dependency",
                    "LineChart responds(with animation) to column chart value selection.", ChartType.OTHER));
            list.add(new ChartSampleDescription(
                    "Tempo Chart",
                    "Presents tempo and height values on a signle chart. Example of multiple axes and reverted Y axis" +
                            " with time format [mm:ss].",
                    ChartType.OTHER));
            list.add(new ChartSampleDescription("Speed Chart",
                    "Presents speed and height values on a signle chart. Exapmle of multiple axes inside chart area.",
                    ChartType.OTHER));
            list.add(new ChartSampleDescription("Good/Bad Chart",
                    "Example of filled area line chart with custom labels", ChartType.OTHER));
            list.add(new ChartSampleDescription("ViewPager with Charts",
                    "Interactive charts within ViewPager. Each chart can be zoom/scroll except pie chart.",
                    ChartType.OTHER));

            return list;
        }
    }


    public static class ChartSamplesAdapter extends ArrayAdapter<ChartSampleDescription> {

        public ChartSamplesAdapter(Context context, int resource, List<ChartSampleDescription> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.list_chart_item, null);

                holder = new ViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.text1);
                holder.text2 = (TextView) convertView.findViewById(R.id.text2);
                holder.chartLayout = (FrameLayout) convertView.findViewById(R.id.chart_layout);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ChartSampleDescription item = getItem(position);

            holder.chartLayout.setVisibility(View.VISIBLE);
            holder.chartLayout.removeAllViews();
            AbstractChartView chart;
            switch (item.chartType) {
                case LINE_CHART:
                    chart = new LineChartView(getContext());
                    holder.chartLayout.addView(chart);
                    break;
//                case COLUMN_CHART:
//                    chart = new ColumnChartView(getContext());
//                    holder.chartLayout.addView(chart);
//                    break;
//                case PIE_CHART:
//                    chart = new PieChartView(getContext());
//                    holder.chartLayout.addView(chart);
//                    break;
//                case BUBBLE_CHART:
//                    chart = new BubbleChartView(getContext());
//                    holder.chartLayout.addView(chart);
//                    break;
//                case PREVIEW_LINE_CHART:
//                    chart = new PreviewLineChartView(getContext());
//                    holder.chartLayout.addView(chart);
//                    break;
//                case PREVIEW_COLUMN_CHART:
//                    chart = new PreviewColumnChartView(getContext());
//                    holder.chartLayout.addView(chart);
//                    break;
                default:
                    chart = null;
                    holder.chartLayout.setVisibility(View.GONE);
                    break;
            }

            if (null != chart) {
                chart.setInteractive(false);// Disable touch handling for chart on the ListView.
            }
            holder.text1.setText(item.text1);
            holder.text2.setText(item.text2);

            return convertView;
        }

        private class ViewHolder {

            TextView text1;
            TextView text2;
            FrameLayout chartLayout;
        }
    }


    public static class ChartSampleDescription {
        String text1;
        String text2;
        ChartType chartType;

        public ChartSampleDescription(String text1, String text2, ChartType chartType) {
            this.text1 = text1;
            this.text2 = text2;
            this.chartType = chartType;
        }
    }
}
