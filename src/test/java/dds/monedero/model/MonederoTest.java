package dds.monedero.model;

import dds.monedero.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void Poner() {
    cuenta.depositar(1500);
    assertEquals(1500, cuenta.getSaldo());
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(CuentaException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void TresDepositos() {
    cuenta.depositar(1500);
    cuenta.depositar(456);
    cuenta.depositar(1900);
    assertEquals(3856, cuenta.getSaldo());
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(CuentaException.class, () -> {
          cuenta.depositar(1500);
          cuenta.depositar(456);
          cuenta.depositar(1900);
          cuenta.depositar(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(CuentaException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.extraer(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000() {
    assertThrows(CuentaException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.extraer(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(CuentaException.class, () -> cuenta.extraer(-500));
  }

}