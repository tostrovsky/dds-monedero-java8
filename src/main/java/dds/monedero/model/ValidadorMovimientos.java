package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ValidadorMovimientos {

  public void validarMontoDeposito(double cuanto, RepositorioMovimientos repo){
    this.validarQueNoExedaDepositosDiarios(repo)
        .validarQueMontoSeaPositivo(cuanto);
  }

  public void validarMontoExtraccion(double cuanto, RepositorioMovimientos repo, BigDecimal saldo){
    this.validarQueExtraccionNoExedaSaldo(cuanto, saldo)
        .validarQueExtraccionNoExedaLimite(cuanto, repo)
        .validarQueMontoSeaPositivo(cuanto);
  }


  public void validarQueMontoSeaPositivo(double cuanto){
    if(cuanto <= 0) throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
  }

  public ValidadorMovimientos validarQueNoExedaDepositosDiarios(RepositorioMovimientos repo) {
    int cantidadLimite = 3;
    if (repo.getMovimientos().stream().filter(Movimiento::isDeposito).count() >= cantidadLimite)
      throw new MaximaCantidadDepositosException("Ya excedio los " + cantidadLimite + " depositos diarios");
      return this;
  }

  public ValidadorMovimientos validarQueExtraccionNoExedaSaldo(double cuanto, BigDecimal saldo){
    if (saldo.doubleValue() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + saldo.doubleValue() + " $");
    }
    return this;
  }

  public ValidadorMovimientos validarQueExtraccionNoExedaLimite(double cuanto, RepositorioMovimientos repo){
    double montoExtraidoHoy = repo.getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
    return this;
  }
}
