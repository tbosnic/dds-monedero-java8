package dds.monedero.model.movimiento;

import dds.monedero.model.Cuenta;

public interface TipoMovimiento {
  void impactarEnCuenta(Cuenta cuenta, double monto);

  Boolean esDeposito();
}
