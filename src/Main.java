
// MACOWINS

  /*      ---Requerimientos---

        1. Saber el precio de venta de una prenda.
        2. Saber el tipo de una prenda.
        3. Registrar una venta.
        4. Saber las ganancias de un dia.
*/




//Aclaracion: hice las cosas con lo que aprendi de los mumukis hasta recien -> puede fallar.

     //   ---Prendas---

import java.util.Date;
import java.util.LinkedList;

class Prenda{

    String tipo; // aunque en principio pense en el tipo como un objeto, al no tener
                //	mayores funcionalidades en el enuncia por el momento, decido modelarlo como un string
                //	hasta que sea necesario un cambio
    double precioOriginal;
    Estado estado;

    Prenda(String tipo, double precioOriginal, Estado estado){
        this.tipo = tipo;
        this.precioOriginal = precioOriginal;
        this.estado = estado;
    }

    double precio(){
        return precioOriginal - estado.descuento(precioOriginal);
    }

    String tipo(){
        return tipo;
    }


}
//---Estados---

interface Estado{

    double descuento(double precioOriginal);
}

class Nueva implements Estado{

    public double descuento(double precioOriginal){
        return 0;
    }
}

class Promocion implements Estado{
    double valorPromocion;

    Promocion(double valorPromocion){
        this.valorPromocion = valorPromocion;
    }

    public double descuento(double precioOriginal){
        return valorPromocion;
    }
}

class Liquidacion implements Estado{

    public double descuento(double precioOriginal){
        return precioOriginal*0.5;
    }
}


//---Tiendas---

class Tienda {

    LinkedList<Venta> ventas = new LinkedList<>();


    void registrarVenta(Venta venta){

       ventas.add(venta);

    }

    int gananciasDia(unaFecha){
        ventasFecha = ventas.stream().filter(venta -> venta.fecha() == unaFecha).collect(Collectors.toList());
        return ventasFecha.stream().map(venta -> venta.montoTotal()).sum();
    }

}
//---Ventas---

class Venta{

    LinkedList<Prenda> prendasVendidas = new LinkedList<>();
    int cantidadPrendasVendidas;
    Date fecha;
    ModoPago modoPago;

    Venta(List<Prenda> prendas, ModoPago pago, Date fecha){
        this.prendasVendidas = prendas;
        this.modoPago = pago;
        this.fecha = fecha;

    }

    void cantidadPrendasVendidas(){
        cantidadPrendasVendidas =  prendasVendidas.size();
    }

    double montoInicial(){
        return prendasVendidas.stream().map(prenda -> prenda.precio()).sum();

    }

    double montoTotal(){
        return modoPago.montoSegunPago(this.montoInicial());
    }

    Date fecha(){
        return fecha;
    }

}


//---Metodos de Pago---

interface ModoPago{

    double montoSegunPago(double montoInicial);

}

class Efectivo implements ModoPago{

    public double montoSegunPago(double montoInicial){
        return montoInicial;
    }
}

class Tarjeta implements ModoPago{
    int cuotas;
    double coeficiente;

    Tarjeta(int cuotas, double coeficiente){
        this.cuotas = cuotas;
        this.coeficiente = coeficiente;
    }

    public double montoSegunPago(double montoInicial){

        return cuotas*coeficiente + 0.01*montoInicial;

    }
}
