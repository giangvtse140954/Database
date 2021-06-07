package giangvt.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import giangvt.example.database.dtos.StudentDTO;

public class StudentDetailActivity extends AppCompatActivity {
    private EditText edtId;
    private EditText edtName;
    private EditText edtMark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtMark = findViewById(R.id.edtMark);
        Intent intent = this.getIntent();
        StudentDTO dto = (StudentDTO) intent.getSerializableExtra("dto");
        edtId.setText(dto.getId());
        edtName.setText(dto.getName());
        edtMark.setText(dto.getMark() + "");
    }
}