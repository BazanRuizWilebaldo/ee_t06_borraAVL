/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolavl1;

/**
 *
 * @author WILY
 */
public class ArbolB {
    Nodo raiz;
    int cant;
    int altura;

    public ArbolB() {
        raiz = null;
    }

    public boolean insertar(int nuevo) {
        if (raiz == null)
            raiz = new Nodo(nuevo, null);
        else {
            Nodo aux = raiz;
            Nodo padre;
            while (true) {
                if (aux.dato == nuevo)
                    return false;
 
                padre = aux;

                boolean irIzq = aux.dato > nuevo;
                aux = irIzq ? aux.izq : aux.der; 
                
                if (aux == null) {
                    if (irIzq) {
                        padre.izq = new Nodo(nuevo, padre);
                    } else {
                        padre.der = new Nodo(nuevo, padre);
                    }
                    rebalanceo(padre);
                    break;
                }
            }
        }
        return true;
    }
 
    public void eliminar(int dato) {
        if (raiz == null)
            return;
        Nodo aux = raiz;
        Nodo padre = raiz;
        Nodo delNodo = null;
        Nodo child = raiz;
 
        while (child != null) {
            padre = aux;
            aux = child;
            child = dato >= aux.dato ? aux.der : aux.izq;
            if (dato == aux.dato)
                delNodo = aux;
        }
 
        if (delNodo != null) {
            delNodo.dato = aux.dato;
            child = aux.izq != null ? aux.izq : aux.der;
 
            if (raiz.dato == dato) {
                raiz = child;
            } else {
                if (padre.izq == aux) {
                    padre.izq = child;
                } else {
                    padre.der = child;
                }
                rebalanceo(padre);
            }
        }
    }
 
    private void rebalanceo(Nodo reba) {
        recibirBalanceo(reba);
 
        if (reba.fe == -2) {
            if (obtenerPeso(reba.izq.izq) >= obtenerPeso(reba.izq.der))
                reba = rotacionIzq(reba);
            else
                reba = rotaCompID(reba);
 
        } else if (reba.fe == 2) {
            if (obtenerPeso(reba.der.der) >= obtenerPeso(reba.der.izq))
                reba = rotacionDer(reba);
            else
                reba = rotaCompDI(reba);
        }
 
        if (reba.padre != null) {
            rebalanceo(reba.padre);
        } else {
            raiz = reba;
        }
    }
   
    private Nodo rotacionDer(Nodo rotaIzq) {
        System.out.println("Rotacion simple a la derecha");
        Nodo aux = rotaIzq.der;
        aux.padre = rotaIzq.padre;
 
        rotaIzq.der = aux.izq;
 
        if (rotaIzq.der != null)
            rotaIzq.der.padre = rotaIzq;
 
        aux.izq = rotaIzq;
        rotaIzq.padre = aux;
 
        if (aux.padre != null) {
            if (aux.padre.der == rotaIzq) {
                aux.padre.der = aux;
            } else {
                aux.padre.izq = aux;
            }
        }
 
        recibirBalanceo(rotaIzq, aux);
 
        return aux;
    }
   
    private Nodo rotacionIzq(Nodo rotaDer) {
         System.out.println("Rotacion simple a la izquierda");

        Nodo temp = rotaDer.izq;
        temp.padre = rotaDer.padre;
 
        rotaDer.izq = temp.der;
 
        if (rotaDer.izq != null)
            rotaDer.izq.padre = rotaDer;
 
        temp.der = rotaDer;
        rotaDer.padre = temp;
 
        if (temp.padre != null) {
            if (temp.padre.der == rotaDer) {
                temp.padre.der = temp;
            } else {
                temp.padre.izq = temp;
            }
        }
 
        recibirBalanceo(rotaDer, temp);
 
        return temp;
    }
 
    private Nodo rotaCompID(Nodo rotaCompID) {
        System.out.println("Rotacion compuesta izquierda a derecha");
        rotaCompID.izq = rotacionDer(rotaCompID.izq);
        return rotacionIzq(rotaCompID);
    }
 
    private Nodo rotaCompDI(Nodo rotaCompDI) {
        System.out.println("Rotacion compuesta derecha a izquierda");
        rotaCompDI.der = rotacionIzq(rotaCompDI.der);
        return rotacionDer(rotaCompDI);
    }
 
    private int obtenerPeso(Nodo aux) {
        if (aux == null)
            return -1;
        return 1 + Math.max(obtenerPeso(aux.der), obtenerPeso(aux.izq));
    }
 
    private void recibirBalanceo(Nodo... nodes) {
        for (Nodo aux : nodes)
            aux.fe = obtenerPeso(aux.der) - obtenerPeso(aux.izq);
    }
}
