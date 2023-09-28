package co.uniminuto.edu.interfaz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContra;
    private ListView listaUsuarios;
    int documento;
    String usuario;
    String nombres;
    String apellidos;
    String contra;

    private GestionBD gestionBD;
    SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializador();
    }
    private void inicializador(){
        etDocumento=findViewById(R.id.ET_documento);
        etUsuario=findViewById(R.id.ET_Usuario);
        etNombres=findViewById(R.id.ET_nombres);
        etApellidos=findViewById(R.id.ET_apellidos);
        etContra=findViewById(R.id.ET_CONTRA);
        listaUsuarios=findViewById(R.id.LV_lista);
        this.listarUsuario();
    }

    public void setearDatos(){
        this.nombres = etNombres.getText().toString();
        this.apellidos = etApellidos.getText().toString();
        this.documento = Integer.parseInt( etDocumento.getText().toString());
        this.contra = etContra.getText().toString();
        this.usuario = etUsuario.getText().toString();
    }

    public void accionRegistar(View v){
        setearDatos();
    UsuarioDao usuarioDao = new UsuarioDao(this,v);
    Usuario usuario1 =  new Usuario();
    usuario1.setNombres(this.nombres);
    usuario1.setApellidos(this.apellidos);
    usuario1.setUsuario(this.usuario);
    usuario1.setContra(this.contra);
    usuario1.setDocumento(this.documento);
    usuarioDao.insertUser(usuario1);
    this.listarUsuario();
    }
    private void listarUsuario() {
    UsuarioDao usuarioDao = new UsuarioDao(this,findViewById(R.id.LV_lista));
        ArrayList<Usuario> userlist = usuarioDao.getUserList();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userlist);
        listaUsuarios.setAdapter(adapter);
    }

    private void eliminarRegistro(){
        setearDatos();


    }

}