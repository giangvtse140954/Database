package giangvt.example.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import giangvt.example.database.daos.StudentDAO;
import giangvt.example.database.dtos.StudentDTO;

public class StudentDetailActivity extends AppCompatActivity {
    private EditText edtId;
    private EditText edtName;
    private EditText edtMark;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtMark = findViewById(R.id.edtMark);
        Intent intent = this.getIntent();
        String action = intent.getStringExtra("action");
        if (action.equals("update")) {
            StudentDTO dto = (StudentDTO) intent.getSerializableExtra("dto");
            edtId.setText(dto.getId());
            edtName.setText(dto.getName());
            edtMark.setText(dto.getMark() + "");
        }
    }

    public void clickToSave(View view) {
        String id = edtId.getText().toString();
        String name = edtName.getText().toString();
        float mark = Float.parseFloat(edtMark.getText().toString());
        StudentDTO dto = new StudentDTO(id, name, mark);
        try {
            StudentDAO dao = new StudentDAO();
            FileInputStream fis = openFileInput("giangvt.txt");
            List<StudentDTO> lisStudent = dao.loadFromInternal(fis);
            FileOutputStream fos = openFileOutput("giangvt.txt", MODE_PRIVATE);

            if (action.equals("create")) {
                lisStudent.add(dto);
            } else if (action.equals("update")) {
                for (StudentDTO studentDTO : lisStudent) {
                    if (studentDTO.getId().equals(dto.getId())) {
                        studentDTO.setName(dto.getName());
                        studentDTO.setMark(dto.getMark());
                        break;
                    }
                }
            }
            dao.saveToInternal(fos, lisStudent);
            Toast.makeText(this, "Save success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}