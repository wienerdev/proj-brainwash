package br.org.pavlov;

import br.org.pavlov.simple.dao.UsuarioDAO;
import br.org.pavlov.simple.model.Usuario;

public class App 
{
    public static void main( String[] args )
    {
        Usuario usu = new Usuario();
        usu.setNome("ETIVALDA");
        
        new UsuarioDAO().save(usu);
    }
}
