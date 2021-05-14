package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void Poner1500DejaUnaCuentaNuevaCon1500() {

    cuenta.poner(1500);
    assertEquals(cuenta.getSaldo(),  new BigDecimal(1500));
  }

  @Test
  void Extraer500DejaUnaCuentaDe4500Con4000() {

    cuenta.setSaldo(new BigDecimal(4500));
    cuenta.sacar(500);
    assertEquals(cuenta.getSaldo(),  new BigDecimal(4000));
  }

  @Test
  void PonerMontoNegativoLanzaExcepcion() {

    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void PonerTresDepositosSeAcumulanEnElSaldoDeLaCuenta() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    double total = 1500 + 456 + 1900;
    assertEquals(cuenta.getSaldo(), new BigDecimal(total));
  }

  @Test
  void HacerMasDeTresDepositosLanzaExcepcion() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldoLanzaExcepcion() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(new BigDecimal(90));
          cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000LanzaExcepcion() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(new BigDecimal(5000));
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativoLanzaExcepcion() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

}