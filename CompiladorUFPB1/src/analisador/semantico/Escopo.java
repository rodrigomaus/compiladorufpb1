/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analisador.semantico;

import java.util.Stack;
import modelo.ErroSemantico;
import modelo.VariavelJaDeclaradaException;
import modelo.VariavelNaoEncontradaException;
import modelo.Variavel;
import modelo.tipos.TipoVariavel;

/**
 *
 * @author clodbrasilino
 */
public class Escopo {

    private Escopo pai;
    private TabelaDeVariaveis variaveis;

    public Escopo(Escopo pai){
        this.pai = pai;
        this.variaveis = new TabelaDeVariaveis();
    }

    public Escopo getPai(){
        return pai;
    }

    public Variavel getVariavel(String identificador, int linha) throws VariavelNaoEncontradaException {
        Variavel procurada;
        procurada = variaveis.get(identificador);
        if(procurada == null){
            if (pai == null){
                throw new VariavelNaoEncontradaException(identificador, linha);
            } else {
                procurada = pai.getVariavel(identificador, linha);
            }
        }
        return procurada;
    }

    public void addVariavel(String identificador, TipoVariavel tipo,int linha) throws VariavelJaDeclaradaException{
        if(variaveis.isDeclarada(identificador)){
            throw new VariavelJaDeclaradaException(identificador, linha);
        }
        else {
            variaveis.create(identificador, tipo);
        }
    }

    public void addVariaveis(Stack<Object> s) throws VariavelJaDeclaradaException, ErroSemantico{
        if(variaveis.areDeclarada(s)){
            throw new VariavelJaDeclaradaException("", 0); // Ver como identificar a linha do erro de lista
        } else {
            variaveis.createAll(s);
        }
    }

    public void addVariavel(String identificador, TipoVariavel tipo, Object valor, int linha) throws VariavelJaDeclaradaException{
        if(variaveis.isDeclarada(identificador)){
            throw new VariavelJaDeclaradaException(identificador, linha);
        }
        else {
            variaveis.create(identificador, tipo, valor);
        }
    }

    public void setVariavel(String identificador, Object valor, int linha) throws VariavelNaoEncontradaException{
        if(variaveis.isDeclarada(identificador)){
            variaveis.get(identificador).setValor(valor);
        } else {
            if (pai == null){
                throw new VariavelNaoEncontradaException(identificador, linha);
            } else {
                pai.setVariavel(identificador, valor, linha);
            }
        }
    }
}