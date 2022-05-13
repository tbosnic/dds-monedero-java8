package dds.monedero.model;

import dds.monedero.exceptions.*;
import dds.monedero.model.movimiento.Deposito;
import dds.monedero.model.movimiento.Movimiento;
import dds.monedero.model.movimiento.TipoMovimiento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void depositar(double cuanto) {
    if (cuanto <= 0) {
      throw new CuentaException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }

    if (getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count() >= 3) {
      throw new CuentaException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  public void extraer(double monto) {
    if (monto <= 0) {
      throw new CuentaException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
    if (getSaldo() - monto < 0) {
      throw new CuentaException("No puede sacar mas de " + getSaldo() + " $");
    }
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (monto > limite) {
      throw new CuentaException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }

    agregarMovimiento(new Deposito(LocalDate.now(), monto));
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
    saldo += movimiento.signoMonto();
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
