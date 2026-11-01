import { useEffect, useState } from 'react'
import api from '../api/axios.js'

export default function Usuarios() {
  const [usuarios, setUsuarios] = useState([])
  const [form, setForm] = useState({ username: '', password: '', nombre: '', rol: 'RECEPCIONISTA', activo: true })
  const [editing, setEditing] = useState(null)
  const [error, setError] = useState('')

  const cargar = async () => {
    const { data } = await api.get('/usuarios')
    setUsuarios(data)
  }
  useEffect(() => { cargar() }, [])

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      if (editing) {
        await api.put(`/usuarios/${editing}`, { ...form, password: form.password || undefined })
      } else {
        await api.post('/usuarios', form)
      }
      setForm({ username: '', password: '', nombre: '', rol: 'RECEPCIONISTA', activo: true })
      setEditing(null)
      cargar()
    } catch (err) {
      setError(err.response?.data?.error || 'Error al guardar')
    }
  }

  const handleEdit = (u) => {
    setEditing(u.id)
    setForm({ username: u.username, password: '', nombre: u.nombre, rol: u.rol, activo: u.activo })
  }

  const handleDelete = async (id) => {
    if (!confirm('Eliminar usuario?')) return
    await api.delete(`/usuarios/${id}`)
    cargar()
  }

  return (
    <div className="container-fluid p-4">
      <h2>Gestion de Usuarios</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="row">
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5>{editing ? 'Editar' : 'Nuevo'} usuario</h5>
              <form onSubmit={handleSubmit}>
                <div className="mb-2">
                  <label className="form-label">Username</label>
                  <input className="form-control" value={form.username} onChange={(e) => setForm({ ...form, username: e.target.value })} required disabled={!!editing} />
                </div>
                <div className="mb-2">
                  <label className="form-label">Password {editing && '(dejar vacio para no cambiar)'}</label>
                  <input type="password" className="form-control" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} required={!editing} />
                </div>
                <div className="mb-2">
                  <label className="form-label">Nombre</label>
                  <input className="form-control" value={form.nombre} onChange={(e) => setForm({ ...form, nombre: e.target.value })} required />
                </div>
                <div className="mb-2">
                  <label className="form-label">Rol</label>
                  <select className="form-select" value={form.rol} onChange={(e) => setForm({ ...form, rol: e.target.value })}>
                    <option value="RECEPCIONISTA">RECEPCIONISTA</option>
                    <option value="REPARADOR">REPARADOR</option>
                    <option value="ADMINISTRADOR">ADMINISTRADOR</option>
                  </select>
                </div>
                <div className="form-check mb-3">
                  <input type="checkbox" className="form-check-input" checked={form.activo} onChange={(e) => setForm({ ...form, activo: e.target.checked })} id="activo" />
                  <label className="form-check-label" htmlFor="activo">Activo</label>
                </div>
                <button className="btn btn-primary w-100" type="submit">{editing ? 'Actualizar' : 'Crear'}</button>
                {editing && <button className="btn btn-secondary w-100 mt-2" type="button" onClick={() => { setEditing(null); setForm({ username: '', password: '', nombre: '', rol: 'RECEPCIONISTA', activo: true }) }}>Cancelar</button>}
              </form>
            </div>
          </div>
        </div>
        <div className="col-md-8">
          <table className="table table-striped">
            <thead><tr><th>Username</th><th>Nombre</th><th>Rol</th><th>Activo</th><th>Acciones</th></tr></thead>
            <tbody>
              {usuarios.map((u) => (
                <tr key={u.id}>
                  <td>{u.username}</td>
                  <td>{u.nombre}</td>
                  <td>{u.rol}</td>
                  <td>{u.activo ? 'Si' : 'No'}</td>
                  <td>
                    <button className="btn btn-sm btn-warning me-1" onClick={() => handleEdit(u)}>Editar</button>
                    <button className="btn btn-sm btn-danger" onClick={() => handleDelete(u.id)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
