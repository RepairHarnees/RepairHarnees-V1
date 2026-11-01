import { useEffect, useState } from 'react'
import api from '../api/axios.js'

export default function Recepciones() {
  const [ordenes, setOrdenes] = useState([])
  const [form, setForm] = useState({ cliente: { nombre: '', telefono: '', email: '', canalNotificacion: 'EMAIL' }, equipo: { marca: '', modelo: '', numeroSerie: '', descripcion: '' }, observaciones: '' })
  const [error, setError] = useState('')

  const cargar = async () => {
    const { data } = await api.get('/ordenes')
    setOrdenes(data)
  }
  useEffect(() => { cargar() }, [])

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      await api.post('/recepciones', form)
      setForm({ cliente: { nombre: '', telefono: '', email: '', canalNotificacion: 'EMAIL' }, equipo: { marca: '', modelo: '', numeroSerie: '', descripcion: '' }, observaciones: '' })
      cargar()
    } catch (err) { setError(err.response?.data?.error || 'Error') }
  }

  const handleDelete = async (codigo) => {
    if (!confirm('Eliminar orden ' + codigo + '?')) return
    await api.delete(`/ordenes/${codigo}`)
    cargar()
  }

  return (
    <div className="container-fluid p-4">
      <h2>Recepciones</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="row">
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5>Nueva recepcion</h5>
              <form onSubmit={handleSubmit}>
                <h6 className="mt-2">Cliente</h6>
                <div className="mb-2"><label className="form-label">Nombre</label><input className="form-control" value={form.cliente.nombre} onChange={(e)=>setForm({...form, cliente:{...form.cliente, nombre:e.target.value}})} required /></div>
                <div className="mb-2"><label className="form-label">Telefono</label><input className="form-control" value={form.cliente.telefono} onChange={(e)=>setForm({...form, cliente:{...form.cliente, telefono:e.target.value}})} /></div>
                <div className="mb-2"><label className="form-label">Email</label><input className="form-control" value={form.cliente.email} onChange={(e)=>setForm({...form, cliente:{...form.cliente, email:e.target.value}})} /></div>
                <div className="mb-2"><label className="form-label">Canal</label>
                  <select className="form-select" value={form.cliente.canalNotificacion} onChange={(e)=>setForm({...form, cliente:{...form.cliente, canalNotificacion:e.target.value}})}>
                    <option value="EMAIL">EMAIL</option><option value="WHATSAPP">WHATSAPP</option><option value="SMS">SMS</option>
                  </select>
                </div>
                <h6 className="mt-3">Equipo</h6>
                <div className="mb-2"><label className="form-label">Marca</label><input className="form-control" value={form.equipo.marca} onChange={(e)=>setForm({...form, equipo:{...form.equipo, marca:e.target.value}})} required /></div>
                <div className="mb-2"><label className="form-label">Modelo</label><input className="form-control" value={form.equipo.modelo} onChange={(e)=>setForm({...form, equipo:{...form.equipo, modelo:e.target.value}})} required /></div>
                <div className="mb-2"><label className="form-label">No. Serie</label><input className="form-control" value={form.equipo.numeroSerie} onChange={(e)=>setForm({...form, equipo:{...form.equipo, numeroSerie:e.target.value}})} /></div>
                <div className="mb-2"><label className="form-label">Descripcion</label><input className="form-control" value={form.equipo.descripcion} onChange={(e)=>setForm({...form, equipo:{...form.equipo, descripcion:e.target.value}})} /></div>
                <div className="mb-2"><label className="form-label">Observaciones</label><input className="form-control" value={form.observaciones} onChange={(e)=>setForm({...form, observaciones:e.target.value})} /></div>
                <button className="btn btn-primary w-100" type="submit">Registrar</button>
              </form>
            </div>
          </div>
        </div>
        <div className="col-md-8">
          <table className="table table-striped">
            <thead><tr><th>Codigo</th><th>Cliente</th><th>Equipo</th><th>Estado</th><th>Fecha</th><th>Acciones</th></tr></thead>
            <tbody>
              {ordenes.map((o) => (
                <tr key={o.ordenId}>
                  <td>{o.codigoInterno}</td><td>{o.clienteNombre}</td><td>{o.equipoMarca} {o.equipoModelo}</td><td>{o.estado}</td><td>{new Date(o.fechaRecepcion).toLocaleString()}</td>
                  <td><button className="btn btn-sm btn-danger" onClick={()=>handleDelete(o.codigoInterno)}>Eliminar</button></td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
