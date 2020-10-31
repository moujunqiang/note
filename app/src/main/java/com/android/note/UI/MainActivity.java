package com.android.note.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.note.Adapter.NoteListAdapter;
import com.android.note.Bean.GlobalValues;
import com.android.note.Bean.NoteBean;
import com.android.note.Bean.TypeBean;
import com.android.note.DB.NoteDao;
import com.android.note.DB.TypeDao;
import com.android.note.R;
import com.android.note.Util.AlarmTimer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NoteDao noteDao;
    private RecyclerView rv_list_main;
    private NoteListAdapter mNoteListAdapter;
    private List<NoteBean> noteList;
    private String login_user;
    private TextView utv;
    private int nav_selected;
    private NavigationView navigationView;
    private Menu menuNav;
    private CircleImageView iv_user;
    private static String path = "/sdcard/Memo/";// sd路径
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav_selected = 2;
        noteDao = new NoteDao(this);

        login_user = "123";
        setTitle("备忘录");
        utv = (TextView) findViewById(R.id.tv_loginuser);
        initData();
        initView();
        registerForContextMenu(rv_list_main);


    }


    //刷新数据库数据，其实对notelist单一更新即可，不必重新获取，但是偷懒了
    private void refreshNoteList() {//mark--0=查询未完成，1=查询已完成，>1=查询所有
        noteList = noteDao.queryNotesAll();
        mNoteListAdapter.setmNotes(noteList);
        mNoteListAdapter.notifyDataSetChanged();
    }

    //初始化数据库数据
    private void initData() {
        Cursor cursor = noteDao.getAllData(login_user);
        noteList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                NoteBean bean = new NoteBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex("note_id")));
                bean.setTitle(cursor.getString(cursor.getColumnIndex("note_tittle")));
                bean.setContent(cursor.getString(cursor.getColumnIndex("note_content")));
                bean.setType(cursor.getString(cursor.getColumnIndex("note_type")));
                bean.setMark(cursor.getInt(cursor.getColumnIndex("note_mark")));
                bean.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
                bean.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
                bean.setOwner(cursor.getString(cursor.getColumnIndex("note_owner")));
                bean.setYear(cursor.getString(cursor.getColumnIndex("year")));
                bean.setMonth(cursor.getString(cursor.getColumnIndex("month")));
                bean.setDay(cursor.getString(cursor.getColumnIndex("day")));
                bean.setAlarm(cursor.getInt(cursor.getColumnIndex("isneedAlarm")));
                noteList.add(bean);
            }
        }
        cursor.close();

    }

    //初始化控件
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fabCalender = (FloatingActionButton) findViewById(R.id.fab);
        fabCalender.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("flag", 0);
                intent.putExtra("login_user", login_user);
                startActivity(intent);
            }
        });

        //抽屉式菜单
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        utv = (TextView) headerLayout.findViewById(R.id.tv_loginuser);
        iv_user = (CircleImageView) headerLayout.findViewById(R.id.iv_user);
        utv.setText(login_user);


        //设置RecyclerView的属性
        rv_list_main = (RecyclerView) findViewById(R.id.rv_list_main);
        rv_list_main.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//竖向列表
        rv_list_main.setLayoutManager(layoutManager);

        mNoteListAdapter = new NoteListAdapter();
        mNoteListAdapter.setmNotes(noteList);
        rv_list_main.setAdapter(mNoteListAdapter);

        //RecyclerViewItem单击事件
        mNoteListAdapter.setOnItemClickListener(new NoteListAdapter.OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, NoteBean note) {
                //Toast.makeText(MainActivity.this,""+note.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //抽屉菜单事件
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_type) {
            startActivity(new Intent(MainActivity.this, TypeManagerActivity.class));

        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage("确定退出备忘录？");
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            builder.setNegativeButton("取消", null);
            builder.create().show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshNoteList();
        //添加默认的分组数据
        TypeDao typeDao = new TypeDao(this);
        List<TypeBean> typeBeans = typeDao.queryTypesAll();
        if (typeBeans == null || typeBeans.size() == 0) {
            TypeBean bean = new TypeBean("默认备忘");
            TypeBean bean1 = new TypeBean("消费备忘");
            TypeBean bean2 = new TypeBean("生活备忘");
            TypeBean bean3 = new TypeBean("学习备忘");
            typeDao.insertNote(bean);
            typeDao.insertNote(bean1);
            typeDao.insertNote(bean2);
            typeDao.insertNote(bean3);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = mNoteListAdapter.getPosition();
        } catch (Exception e) {

            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case Menu.FIRST + 1://查看该笔记
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", noteList.get(position));
                intent.putExtra("data", bundle);
                startActivity(intent);
                break;

            case Menu.FIRST + 2://编辑该笔记
                Intent intent2 = new Intent(MainActivity.this, EditActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("note", noteList.get(position));
                intent2.putExtra("data", bundle2);
                intent2.putExtra("flag", 1);//编辑笔记
                startActivity(intent2);
                break;

            case Menu.FIRST + 3://删除该笔记
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定删除笔记？");
                builder.setCancelable(false);
                final int finalPosition = position;
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int ret = noteDao.DeleteNote(noteList.get(finalPosition).getId());
                        if (ret > 0) {
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            refreshNoteList();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;


            case Menu.FIRST + 6://设置提醒时间
                NoteBean bean3 = noteList.get(position);
                selectTime(bean3.getId(), bean3, position);
                break;
            case Menu.FIRST + 7://清除提醒
                NoteBean noteBean = noteList.get(position);
                AlarmTimer.cancelAlarmTimer(this, GlobalValues.TIMER_ACTION, noteBean.getId());
                noteBean.setAlarm(0);
                noteDao.updateNote(noteBean);
                //noteList.get(position).setMark(0);
                refreshNoteList();
                mNoteListAdapter.notifyItemRangeChanged(position, position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void selectTime(final int alarmId, final NoteBean noteBean, final int position) {

        calendar = Calendar.getInstance();
        DatePickerDialog dpdialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int day) {
                        // TODO Auto-generated method stub
                        // 更新EditText控件日期 小于10加0
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        final TimePickerDialog tpdialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR, i);
                calendar.set(Calendar.MINUTE, i1);
                long timeInMillis = calendar.getTimeInMillis();
                noteBean.setAlarm(1);
                noteDao.updateNote(noteBean);
                AlarmTimer.setAlarmTimer(MainActivity.this, timeInMillis, GlobalValues.TIMER_ACTION, AlarmManager.RTC_WAKEUP, alarmId, noteBean);

                refreshNoteList();
                mNoteListAdapter.notifyItemRangeChanged(position, position);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        dpdialog.show();
        dpdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                tpdialog.show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
            return;
        } else {
            Toast.makeText(getBaseContext(), "再按一次返回退出程序", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }
}

