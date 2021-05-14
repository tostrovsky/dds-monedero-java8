package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private BigDecimal saldo;
  private final RepositorioMovimientos repo= new RepositorioMovimientos();

  public Cuenta() {
    saldo = BigDecimal.valueOf(0);
  }

  public Cuenta(double montoInicial) {
    saldo = BigDecimal.valueOf(montoInicial);
  }

  public void poner(double cuanto) {
    this.repo.getValidador().validarMontoDeposito(cuanto, this.repo);
    new Movimiento(LocalDate.now(), new BigDecimal(cuanto), true).agregateA(this);
  }

  public void sacar(double cuanto) {
    this.repo.getValidador().validarMontoExtraccion(cuanto, this.repo, getSaldo());
    new Movimiento(LocalDate.now(), new BigDecimal(cuanto), false).agregateA(this);
  }

  public RepositorioMovimientos getRepositorioMovimientos(){
    return this.repo;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }

}
