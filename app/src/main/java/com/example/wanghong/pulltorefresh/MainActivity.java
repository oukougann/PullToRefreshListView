package com.example.wanghong.pulltorefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView listView;
    private PullToRefreshListViewSampleAdapter adapter;

    // IDs for the context menu actions
    private final int idEdit = 1;
    private final int idDelete = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);

        // OPTIONAL: Disable scrolling when list is refreshing
        // listView.setLockScrollWhileRefreshing(false);

        // OPTIONAL: Uncomment this if you want the Pull to Refresh header to show the 'last updated' time
        // listView.setShowLastUpdatedText(true);

        // OPTIONAL: Uncomment this if you want to override the date/time format of the 'last updated' field
        // listView.setLastUpdatedDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));

        // OPTIONAL: Uncomment this if you want to override the default strings
        // listView.setTextPullToRefresh("Pull to Refresh");
        // listView.setTextReleaseToRefresh("Release to Refresh");
        // listView.setTextRefreshing("Refreshing");

        // MANDATORY: Set the onRefreshListener on the list. You could also use
        // listView.setOnRefreshListener(this); and let this Activity
        // implement OnRefreshListener.
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                        adapter.refreshData();
                    }
                }, 2000);
            }
        });

        adapter = new PullToRefreshListViewSampleAdapter() {};
        listView.setAdapter(adapter);

        // Request the adapter to load the data
        adapter.loadData();

        // click listener
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                PullToRefreshListViewSampleAdapter.ViewHolder viewHolder = (PullToRefreshListViewSampleAdapter.ViewHolder) arg1.getTag();
                if (viewHolder.name != null){
                    Toast.makeText(MainActivity.this, viewHolder.name.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Register the context menu for actions
        registerForContextMenu(listView);
    }


    /**
     * Create the context menu with the Edit and Delete options
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Add any actions you need. Implement the logic in onContextItemSelected
        menu.add(Menu.NONE, idEdit, Menu.NONE, R.string.edit);
        menu.add(Menu.NONE, idDelete, Menu.NONE, R.string.delete);
    }

    /**
     * Event called after an option from the context menu is selected
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case idEdit:

                // Put your code here for Edit action
                // just as an example a toast message
                Toast.makeText(this, getString(R.string.edit) + " " + adapter.getItem(info.position-1), Toast.LENGTH_SHORT).show();
                return true;
            case idDelete:

                // Put your code here for Delete action
                // just as an example a toast message
                Toast.makeText(this, getString(R.string.delete) + " " + adapter.getItem(info.position-1), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    /**
     * The adapter used to display the results in the list
     *
     */
    public abstract class PullToRefreshListViewSampleAdapter extends BaseAdapter {

        private ArrayList<String> items = new ArrayList<String>();;

        public class ViewHolder {
            public String id;
            public TextView name;
        }

        /**
         * Loads the data.
         */
        public void loadData() {

            // Here add your code to load the data for example from a webservice or DB

            items = new ArrayList<String>();

            items.add("Ajax Amsterdam");
            items.add("Barcelona");
            items.add("Manchester United");
            items.add("Chelsea");
            items.add("Real Madrid");
            items.add("Bayern Munchen");
            items.add("Internazionale");
            items.add("Valencia");
            items.add("Arsenal");
            items.add("AS Roma");
            items.add("Tottenham Hotspur");
            items.add("PSV");
            items.add("Olympique Lyon");
            items.add("AC Milan");
            items.add("Dortmund");
            items.add("Schalke 04");
            items.add("Twente");
            items.add("Porto");
            items.add("Juventus");


            // MANDATORY: Notify that the data has changed
            notifyDataSetChanged();
        }

        private int n = 1;
        public void refreshData() {
            items = new ArrayList<String>();
            items.add("Ajax Amsterdam"+n);
            items.add("Barcelona"+n);
            items.add("Manchester United"+n);
            items.add("Chelsea"+n);
            items.add("Real Madrid"+n);
            items.add("Bayern Munchen"+n);
            items.add("Internazionale"+n);
            items.add("Valencia"+n);
            items.add("Arsenal"+n);
            items.add("AS Roma"+n);
            items.add("Tottenham Hotspur"+n);
            items.add("PSV"+n);
            items.add("Olympique Lyon"+n);
            items.add("AC Milan"+n);
            items.add("Dortmund"+n);
            items.add("Schalke 04"+n);
            items.add("Twente"+n);
            items.add("Porto"+n);
            items.add("Juventus"+n);
            notifyDataSetChanged();

            n++;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            String record = (String) getItem(position);

            LayoutInflater inflater = MainActivity.this.getLayoutInflater();

            ViewHolder viewHolder = new ViewHolder();

            if (convertView == null){
                rowView = inflater.inflate(R.layout.list_item,null);

                viewHolder.name = (TextView) rowView.findViewById(R.id.textView1);

                rowView.setTag(viewHolder);
            }

            final ViewHolder holder = (ViewHolder) rowView.getTag();

            holder.name.setText(record);

            return rowView;
        }
    }
}
