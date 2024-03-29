package giangvt.example.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import giangvt.example.database.daos.StudentAdapter;
import giangvt.example.database.daos.StudentDAO;
import giangvt.example.database.databinding.ActivityMainBinding;
import giangvt.example.database.dtos.StudentDTO;

public class MainActivity extends AppCompatActivity {
    private ListView listViewStudent;
    private TextView txtTitle;
    private StudentAdapter adapter;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void clickToLoadFromRAW(MenuItem item) {
        listViewStudent = findViewById(R.id.listViewStudent);
        txtTitle = findViewById(R.id.txtTitle);
//        System.out.println("load raw");
        try {
            adapter = new StudentAdapter();
            txtTitle.setText("List Student from RAW");
            InputStream is = getResources().openRawResource(R.raw.text);
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> result = dao.loadFromRAW(is);
            adapter.setListStudents(result);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, StudentDetailActivity.class);
                    intent.putExtra("action", "update");
                    intent.putExtra("dto", dto);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToSaveFromRAWToInternal(MenuItem item) {
        try {

            listViewStudent = findViewById(R.id.listViewStudent);
            txtTitle = findViewById(R.id.txtTitle);
            StudentDAO dao = new StudentDAO();
            InputStream is = getResources().openRawResource(R.raw.text);
            List<StudentDTO> list = dao.loadFromRAW(is);
            FileOutputStream fos = openFileOutput("giangvt.txt", MODE_PRIVATE);
            dao.saveToInternal(fos, list);
            Toast.makeText(this, "Save success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToLoadFromInternal(MenuItem item) {
        try {

            listViewStudent = findViewById(R.id.listViewStudent);
            txtTitle = findViewById(R.id.txtTitle);

            adapter = new StudentAdapter();
            txtTitle.setText("List Student from Internal");
            FileInputStream fis = openFileInput("giangvt.txt");
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> listStudent = dao.loadFromInternal(fis);
            adapter.setListStudents(listStudent);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, StudentDetailActivity.class);
                    intent.putExtra("action", "update");
                    intent.putExtra("dto", dto);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToCreate(View view) {
        Intent intent = new Intent(this, StudentDetailActivity.class);
        intent.putExtra("action", "create");
        startActivity(intent);
    }
    
    public void clickToSaveToExternal(MenuItem item) {
        try {
            FileInputStream fis = openFileInput("giangvt.txt");
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> list = dao.loadFromInternal(fis);
            dao.saveToExternal(list);
            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToLoadFromExternal(MenuItem item) {
    }
}