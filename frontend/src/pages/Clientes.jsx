import { useEffect, useState } from 'react'
import api from '../api/axios.js'

export default function Clientes() {
  const [clientes, setClientes] = useState([])
  const [form, setForm] = useState({ nombre: '', telefono: '', email: '', canalNotificacion: 'EMAIL' })
  const [editing, setEditing] = useState(null)
  const [error, setError] = useState('')
  const [recepcionForm, setRecepcionForm] = useState(null)

  const cargar = async () => {
    const { data } = await api.get('/clientes')
    setClientes(data)
  }
  useEffect(() => { cargar() }, [])

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      if (editing) await api.put(`/clientes/${editing}`, form)
      else await api.post('/clientes', form)
      setForm({ nombre: '', telefono: '', email: '', canalNotificacion: 'EMAIL' })
      setEditing(null)
      cargar()
    } catch (err) { setError(err.response?.data?.error || 'Error') }
  }

  const handleEdit = (c) => { setEditing(c.id); setForm({ nombre: c.nombre, telefono: c.telefono, email: c.email, canalNotificacion: c.canalNotificacion }) }
  const handleDelete = async (id) => { if (!confirm('Eliminar cliente?')) return; await api.delete(`/clientes/${id}`); cargar() }

  const handleRecepcion = async (e) => {
    e.preventDefault()
    try {
      const { data } = await api.post(`/clientes/${recepcionForm.id}/recepciones`, {
        equipo: recepcionForm.equipo,
        observaciones: recepcionForm.observaciones
      })
      alert('Recepcion creada: ' + data.codigoInterno)
      setRecepcionForm(null)
    } catch (err) { alert(err.response?.data?.error || 'Error') }
  }

  return (
    <div className="container-fluid p-4">
      <h2>Clientes</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="row">
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5>{editing ? 'Editar' : 'Nuevo'} cliente</h5>
              <form onSubmit={handleSubmit}>
                <div className="mb-2"><label className="form-label">Nombre</label><input className="form-control" value={form.nombre} onChange={(e)=>setForm({...form,nombre:e.target.value})} required /></div>
                <div className="mb-2"><label className="form-label">Telefono</label><input className="form-control" value={form.telefono} onChange={(e)=>setForm({...form,telefono:e.target.value})} /></div>
                <div className="mb-2"><label className="form-label">Email</label><input className="form-control" value={form.email} onChange={(e)=>setForm({...form,email:e.target.value})} /></div>
                <div className="mb-2"><label className="form-label">Canal</label>
                  <select className="form-select" value={form.canalNotificacion} onChange={(e)=>setForm({...form,canalNotificacion:e.target.value})}>
                    <option value="EMAIL">EMAIL</option><option value="WHATSAPP">WHATSAPP</option><option value="SMS">SMS</option>
                  </select>
                </div>
                <button className="btn btn-primary w-100" type="submit">{editing?'Actualizar':'Crear'}</button>
                {editing && <button className="btn btn-secondary w-100 mt-2" type="button" onClick={()=>{setEditing(null);setForm({nombre:'',telefono:'',email:'',canalNotificacion:'EMAIL'})}}>Cancelar</button>}
              </form>
            </div>
          </div>
        </div>
        <div className="col-md-8">
          <table className="table table-striped">
            <thead><tr><th>Nombre</th><th>Telefono</th><th>Email</th><th>Canal</th><th>Acciones</th></tr></thead>
            <tbody>
              {clientes.map((c) => (
                <tr key={c.id}>
                  <td>{c.nombre}</td><td>{c.telefono}</td><td>{c.email}</td><td>{c.canalNotificacion}</td>
                  <td>
                    <button className="btn btn-sm btn-info me-1" onClick={()=>setRecepcionForm({id:c.id, equipo:{marca:'',modelo:'',numeroSerie:'',descripcion:''}, observaciones:''})}>+ Recepcion</button>
                    <button className="btn btn-sm btn-warning me-1" onClick={()=>handleEdit(c)}>Editar</button>
                    <button className="btn btn-sm btn-danger" onClick={()=>handleDelete(c.id)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {recepcionForm && (
        <div className="modal show d-block" style={{background:'rgba(0,0,0,0.5)'}}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header"><h5 className="modal-title">Nueva recepcion</h5><button className="btn-close" onClick={()=>setRecepcionForm(null)}></button></div>
              <div className="modal-body">
                <form onSubmit={handleRecepcion}>
                  <div className="mb-2"><label className="form-label">Marca</label><input className="form-control" value={recepcionForm.equipo.marca} onChange={(e)=>setRecepcionForm({...recepcionForm, equipo:{...recepcionForm.equipo, marca:e.target.value}})} required /></div>
                  <div className="mb-2"><label className="form-label">Modelo</label><input className="form-control" value={recepcionForm.equipo.modelo} onChange={(e)=>setRecepcionForm({...recepcionForm, equipo:{...recepcionForm.equipo, modelo:e.target.value}})} required /></div>
                  <div className="mb-2"><label className="form-label">No. Serie</label><input className="form-control" value={recepcionForm.equipo.numeroSerie} onChange={(e)=>setRecepcionForm({...recepcionForm, equipo:{...recepcionForm.equipo, numeroSerie:e.target.value}})} /></div>
                  <div className="mb-2"><label className="form-label">Descripcion</label><input className="form-control" value={recepcionForm.equipo.descripcion} onChange={(e)=>setRecepcionForm({...recepcionForm, equipo:{...recepcionForm.equipo, descripcion:e.target.value}})} /></div>
                  <div className="mb-2"><label className="form-label">Observaciones</label><input className="form-control" value={recepcionForm.observaciones} onChange={(e)=>setRecepcionForm({...recepcionForm, observaciones:e.target.value})} /></div>
                  <button className="btn btn-primary w-100" type="submit">Crear recepcion</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
