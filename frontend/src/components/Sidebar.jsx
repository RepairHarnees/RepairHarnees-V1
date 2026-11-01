import { NavLink } from 'react-router-dom'
import { useAuth } from '../context/AuthContext.jsx'

const menuByRole = {
  ADMINISTRADOR: [
    { to: '/', label: 'Dashboard', icon: 'bi-speedometer2' },
    { to: '/usuarios', label: 'Gestion de Usuarios', icon: 'bi-people' },
    { to: '/clientes', label: 'Clientes', icon: 'bi-person-badge' },
    { to: '/recepciones', label: 'Recepciones', icon: 'bi-clipboard-check' },
    { to: '/ordenes', label: 'Ordenes', icon: 'bi-list-check' },
    { to: '/comprobantes', label: 'Comprobantes', icon: 'bi-file-earmark-pdf' },
    { to: '/logs', label: 'Logs de Auditoria', icon: 'bi-journal-text' }
  ],
  RECEPCIONISTA: [
    { to: '/', label: 'Dashboard', icon: 'bi-speedometer2' },
    { to: '/clientes', label: 'Clientes', icon: 'bi-person-badge' },
    { to: '/recepciones', label: 'Recepciones', icon: 'bi-clipboard-check' },
    { to: '/ordenes', label: 'Ordenes', icon: 'bi-list-check' },
    { to: '/comprobantes', label: 'Comprobantes', icon: 'bi-file-earmark-pdf' }
  ],
  REPARADOR: [
    { to: '/', label: 'Dashboard', icon: 'bi-speedometer2' }
  ]
}

export default function Sidebar() {
  const { user, logout } = useAuth()
  const items = (user && menuByRole[user.rol]) || []

  return (
    <div className="d-flex flex-column bg-dark text-white p-3" style={{ width: '260px', minHeight: '100vh' }}>
      <h5 className="mb-4">RepairHarnees</h5>
      <div className="mb-3 small text-secondary">
        {user?.username} <br />
        <span className="badge bg-info text-dark">{user?.rol}</span>
      </div>
      <ul className="nav nav-pills flex-column mb-auto">
        {items.map((it) => (
          <li className="nav-item mb-1" key={it.to}>
            <NavLink to={it.to} end className={({ isActive }) => 'nav-link text-white ' + (isActive ? 'active bg-primary' : '')}>
              <i className={`bi ${it.icon} me-2`}></i>{it.label}
            </NavLink>
          </li>
        ))}
      </ul>
      <button className="btn btn-outline-light btn-sm mt-3" onClick={logout}>Cerrar sesion</button>
    </div>
  )
}
