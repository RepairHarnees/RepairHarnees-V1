import { useAuth } from '../context/AuthContext.jsx'

export default function Dashboard() {
  const { user } = useAuth()
  return (
    <div className="container-fluid p-4">
      <h2>Dashboard</h2>
      <p className="text-muted">Bienvenido, {user?.username} ({user?.rol})</p>
      <div className="row g-3 mt-2">
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Recepciones</h5>
              <p className="card-text">Gestion de recepcion de equipos.</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Ordenes</h5>
              <p className="card-text">Seguimiento de ordenes de servicio.</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Comprobantes</h5>
              <p className="card-text">Emision de comprobantes en PDF.</p>
            </div>
          </div>
        </div>
      </div>
      {user?.rol === 'REPARADOR' && (
        <div className="alert alert-info mt-4">
          No hay funciones asignadas a tu rol por ahora.
        </div>
      )}
    </div>
  )
}
