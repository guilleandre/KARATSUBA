
package karatsuba;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.BigInteger;

/**
 * @author GUILLERMO ANDRES SANCHEZ CESPEDES
 */
//public class Karatsuba extends JFrame implements ActionListener {
public class Karatsuba extends JFrame implements ActionListener {
    /**
     * @param args the command line arguments
     */
    JPanel P0;
    JLabel L1,L2;
    JTextField T1,T2,T3;
    JButton b0;
   
    public Karatsuba ()
    {
 super( "KARATSUBA" ); //Nombre de la aplicación
 //Formato del Panel
 P0= new JPanel(new GridLayout( 6, 1 ,2,5));
 // Definicion de las Etiquetas
 L1= new JLabel ("Numero 1",SwingConstants.CENTER);
 L2= new JLabel ("Numero 2",SwingConstants.CENTER);
 // Definicion de los campos de texto
 T1= new JTextField ();
 T2= new JTextField ();
 T3= new JTextField ();
 // Definicion del Boton para calcular el algoritmo
 b0= new JButton("Resultado");
 b0.addActionListener(this);
 //Organizacion del Panel
 P0.add(L1);
 P0.add(T1);
 P0.add(L2);
 P0.add(T2);
 P0.add(b0);
 P0.add(T3);
 add(P0); // adicioanr el panel
    setSize(270, 270 ); //Tamaño de la ventana
    setVisible( true );
}
   
    public BigInteger calculok(BigInteger x, BigInteger y)
    {
       //Se trabajo con el valor absoluto para no tener problemas con el valor del signo en el algoritmo
        x=x.abs();
        y=y.abs();
       // Obtencion de la longitud de los numeros para dividirlos
        int xb= x.bitLength();
        int yb= y.bitLength();
        // Condiciones de terminacion del calculo del Karatsuba
        if (xb==0 || yb==0)
			return ( BigInteger.ZERO);
		if (xb == 1)
			return y;
		if (yb == 1)
			return x;
                
		int n = Math.max(xb, yb);
               // Ecuaciones del KARATSUBA x*y=(x1*y1)B^2m  + (x1*y0+x0*y1)B^m + (x0*y0);
                int m=n/2;
                //Division de los numeros
		BigInteger x1 = x.shiftRight(m);
		BigInteger x0 = x.xor(x1.shiftLeft(m));
		BigInteger y1 = y.shiftRight(m);
		BigInteger y0= y.xor(y1.shiftLeft(m));
		// Las 3 multiplicaciones mas simples y que se usan recursivamente con el karatsuba
		BigInteger z2 = calculok(x1, y1);                   //z2=x1*x2
		BigInteger z1 = calculok(x1.add(x0),y1.add(y0));    //z1=(x1+x0)*(y1*y0)
		BigInteger z0 = calculok(x0, y0);                   //z0=x0*y0
		//x*y=(z2)B^2m  + (z1-z2-z0)B^m + (k0)
		BigInteger k2 = z2.shiftLeft(2*m);
		BigInteger k1 = z1.subtract(z2).subtract(z0).shiftLeft(m);
		BigInteger k0 = z0;
		//Resultado del KARATSUBA x*y=k2 + k1 +k0
		return k2.add(k1).add(k0);
    }

    public static void main(String[] args) {
        Karatsuba aplicacion0 = new Karatsuba();
       aplicacion0.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        int Error=0; //inicializacion de la variable que indica error en los numeros digitados
        try{       
            new BigInteger(T1.getText());    
            new BigInteger(T2.getText());
              }
                  catch(NumberFormatException ex) //Manejo de la Excepcion para aclarar al usuario los valores correctos 
                  {
                  Error=1;
                  T3.setText("Digite 2 numeros enteros por favor");
                  }
        if (Error ==0){
            BigInteger x= new BigInteger(T1.getText()); //obtencion del primer dato a multiplicar en formato BigInteger
            BigInteger y= new BigInteger(T2.getText()); //obtencion del segundo dato a multiplicar en formato BigInteger
           //Manejo del signo al mostrar la respuesta
           if((x.signum()==-1 && y.signum()==1) || (y.signum()==-1 && x.signum()==1))
           T3.setText("-"+calculok(x,y));
           else
           T3.setText(""+calculok(x,y));    
        }
    }
}