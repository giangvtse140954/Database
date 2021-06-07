package giangvt.example.database.daos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import giangvt.example.database.dtos.StudentDTO;

public class StudentDAO {
    public StudentDAO () {}
    public List<StudentDTO> loadFromRAW(InputStream is) throws Exception {
        List<StudentDTO> result = new ArrayList<>();
        StudentDTO dto = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        String s = null;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((s = br.readLine()) != null) {
                String[] tmp = s.split("-");
                dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
        }
        return result;
    }
    public List<StudentDTO> loadFromInternal(FileInputStream fis) throws Exception {
        List<StudentDTO> result = new ArrayList<>();
        StudentDTO dto = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        String s = null;
        try {
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            while ((s = br.readLine()) != null) {
                String[] tmp = s.split("-");
                dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
        }
        return result;
    }
    public void saveToInternal(FileOutputStream fos, List<StudentDTO> list) throws Exception {
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        String result = "";
        for (StudentDTO dto : list) {
            result += dto.toString() + "\n";
        }
        osw.write(result);
        osw.flush();
//        finally
        osw.close();
    }
}
