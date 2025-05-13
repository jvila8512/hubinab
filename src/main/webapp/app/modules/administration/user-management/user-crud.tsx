import React, { useEffect, useRef, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {
  getUsersAsAdmin,
  getUsersTodos,
  updateUser,
  getReportePdf,
  deleteUser,
  createUser,
  getRolesJson,
  getRolesJsonok,
  reset,
} from './user-management.reducer';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Translate, ValidatedField, ValidatedForm, isEmail, translate } from 'react-jhipster';
import { Calendar } from 'primereact/calendar';
import dayjs from 'dayjs';
import { Toolbar } from 'primereact/toolbar';
import { DataTable, DataTableFilterMeta } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Avatar } from 'primereact/avatar';
import { Badge } from 'primereact/badge';
import { Dialog } from 'primereact/dialog';
import { Col, Row } from 'reactstrap';
import { languages, locales } from 'app/config/translation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { MultiSelect } from 'primereact/multiselect';
import { Chip } from 'primereact/chip';

export const UserCrud = (props: RouteComponentProps<any>) => {
  const dispatch = useAppDispatch();
  const { match } = props;
  const account = useAppSelector(state => state.authentication.account);
  const users = useAppSelector(state => state.userManagement.users);
  const loading = useAppSelector(state => state.userManagement.loading);
  const updating = useAppSelector(state => state.userManagement.updating);
  const updateSuccess = useAppSelector(state => state.userManagement.updateSuccess);

  const authorities = useAppSelector(state => state.userManagement.authorities);
  const isInvalid = false;

  const [isNew, setNew] = useState(true);
  const dt = useRef(null);
  const emptyUser = {
    id: '',
    login: '',
    firstName: '',
    lastName: '',
    email: '',
    activated: true,
    langKey: '',
    imageUrl: '',
    imagen: '',

    authorities: [],
    createdBy: '',
    createdDate: null,
    lastModifiedBy: '',
    lastModifiedDate: null,
    password: '',
  };
  const [usuario, setUsuario] = useState(null);
  const [selectedUsuario, setSelectedUsuario] = useState(emptyUser);

  const fileUploadRef = useRef(null);
  const file = useAppSelector(state => state.files.entity);
  const updatingFile = useAppSelector(state => state.files.updating);
  const updateSuccessFile = useAppSelector(state => state.files.updateSuccess);
  const loadingFile = useAppSelector(state => state.files.loading);
  const [selectedFile, setSelectedFile] = useState(null);
  const [fileModificar, setfileModificar] = useState(null);

  const [dialog, setDialog] = useState(false);
  const [dialogNew, setDialogNew] = useState(false);
  const [deleteDialog, setDeleteDialog] = useState(false);

  const [globalFilter, setGlobalFilterValue] = useState('');

  const [selectedUserChip, setselectedUserChip] = useState(null);
  const [listRoles, setselectedLisRoles] = useState(null);

  useEffect(() => {
    dispatch(getUsersTodos());
    dispatch(getRolesJson());

    const retosFiltrar = getRolesJsonok();
    retosFiltrar.then(response => {
      setselectedLisRoles(response.data);
    });
  }, []);

  const [filters, setFilters] = useState({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    login: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    firstName: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
    lastName: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
  });
  const onGlobalFilterChange = e => {
    const value = e.target.value;
    const _filters = { ...filters };
    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  const confirmDeleteSelected = () => {
    setDeleteDialog(true);
  };

  useEffect(() => {
    if (updateSuccess) {
      setDialogNew(false);
      setselectedUserChip(null);
      setUsuario(null);
    }
  }, [updateSuccess]);

  const verDialogNuevo = () => {
    setDialogNew(true);
    setUsuario(null);
    setNew(true);
  };
  const actualizar = objeto => {
    setUsuario({ ...objeto });
    // Convertir el array de strings a un array de objetos con un solo atributo "name"
    const objetos = objeto.authorities.map(string => ({ name: string }));
    setselectedUserChip(objetos);
    setDialogNew(true);
    setNew(false);
  };
  const atras = () => {
    props.history.push(`/usuario-panel/`);
  };

  const leftToolbarTemplate = () => {
    return (
      <React.Fragment>
        <div className="my-2">
          <Button label="Atrás" icon="pi pi-arrow-left" className="p-button-secondary mr-2" onClick={atras} />
        </div>
      </React.Fragment>
    );
  };

  const rightToolbarTemplate = () => {
    return (
      <React.Fragment>
        <Button label="Nuevo Usuario" icon="pi pi-plus" className="p-button-info" onClick={verDialogNuevo} />
      </React.Fragment>
    );
  };
  const header = (
    <div className="grid">
      <div className="col">
        <div className="flex justify-content-start  font-bold  m-2">
          <div className="text-primary text-xl">Usuarios</div>
        </div>
      </div>

      <div className="col">
        <div className="flex align-items-center justify-content-end  m-2">
          <span className=" block  p-input-icon-left">
            <i className="pi pi-search" />
            <InputText value={globalFilter} type="search" onInput={onGlobalFilterChange} placeholder="Buscar..." />
          </span>
        </div>
      </div>
    </div>
  );

  const imagen = user => {
    return <>{user?.imageUrl && <Avatar image={`content/uploads/${user.imageUrl}`} shape="circle" size="xlarge"></Avatar>}</>;
  };
  const toggleActive = user => {
    dispatch(
      updateUser({
        ...user,
        activated: !user.activated,
      })
    );
  };

  const login = user => {
    return <>{user?.login}</>;
  };
  const email = user => {
    return <div className="flex">{user?.email}</div>;
  };
  const activado = user => {
    return (
      <>
        {user?.activated ? (
          <Button
            label="Activado"
            className="p-button-success"
            onClick={() => toggleActive(user)}
            disabled={account.login === user?.login}
          ></Button>
        ) : (
          <Button
            label="Desactivado"
            className="p-button-danger"
            onClick={() => toggleActive(user)}
            disabled={account.login === user?.login}
          ></Button>
        )}
      </>
    );
  };
  const roles = user => {
    return (
      <div className="flex flex-column ">
        {user?.authorities
          ? user.authorities.map((authority, j) => (
              <Chip key={`user-auth-${j}`} label={authority.replace('ROLE_', '')} className=" custom-chip mr-3 mt-1" />
            ))
          : null}
      </div>
    );
  };
  const createdDate = user => {
    return (
      <div className="flex align-items-center justify-content-center">
        {user?.createdDate ? dayjs(user.createdDate).utc().format('dddd, D MMMM , YYYY h:mm A') : null}
      </div>
    );
  };
  const lastModifiedBy = user => {
    return <>{user?.lastModifiedBy}</>;
  };
  const lastModifiedDate = user => {
    return <>{user?.lastModifiedDate ? dayjs(user?.lastModifiedDate).utc().format('dddd, D MMMM , YYYY h:mm A') : null}</>;
  };
  const verBorrar = user => {
    setDeleteDialog(true);
    setSelectedUsuario(user);
  };
  const ver = user => {
    setSelectedUsuario(user);
    setDialog(true);
  };
  const actionBodyTemplate = rowData => {
    return (
      <div className=" flex align-items-center justify-content-center">
        <Button
          icon="pi pi-trash"
          className="p-button-rounded p-button-danger ml-2 mb-1"
          disabled={account.login === rowData?.login}
          onClick={() => verBorrar(rowData)}
        />

        <Button icon="pi pi-pencil" className="p-button-rounded p-button-warning ml-2 mb-1" onClick={() => actualizar(rowData)} />
      </div>
    );
  };
  const deleteUsuario = () => {
    dispatch(deleteUser(selectedUsuario.login));
    setDeleteDialog(false);
  };

  const hideDeleteDialog = () => {
    setDeleteDialog(false);
    setSelectedUsuario(null);
  };
  const deleteDialogFooter = (
    <>
      <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteDialog} />

      <Button label="Sí" icon="pi pi-check" className="p-button-text" onClick={deleteUsuario} />
    </>
  );
  const hideDialog = () => {
    setDialog(false);
    setNew(true);
  };

  const dialogFooter = (
    <>
      <Button label="Cerrar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
    </>
  );
  const hideDialogNuevo = () => {
    setDialogNew(false);
    setselectedUserChip(null);
    setUsuario(null);
  };
  const saveUser = values => {
    const rolesss = selectedUserChip ? selectedUserChip.map(objeto => objeto.name) : [];
    const entity = {
      ...values,
      authorities: rolesss,
    };
    if (isNew) {
      dispatch(createUser(entity));
    } else {
      dispatch(updateUser(entity));
    }
  };
  const rolesTemplate = option => {
    const roleName = option.name.replace('ROLE_', ''); // Eliminar 'ROLE_' del nombre
    return (
      <div className="country-item">
        <div>{roleName}</div>
      </div>
    );
  };

  const selectedRolesTemplate = option => {
    if (option) {
      const roleName = option.name.replace('ROLE_', ''); // Eliminar 'ROLE_' del nombre
      return (
        <div className="country-item country-item-value">
          <Chip label={roleName} className=" custom-chip mr-3 " />
        </div>
      );
    }

    return 'Seleccione Rol';
  };

  return (
    <div className="grid crud-demo mt-3 mb-4">
      <div className="col-12">
        <div className="card">
          <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

          <DataTable
            ref={dt}
            value={users}
            selection={selectedUsuario}
            onSelectionChange={e => setSelectedUsuario(e.value)}
            dataKey="id"
            paginator
            rows={10}
            rowsPerPageOptions={[5, 10, 25]}
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            currentPageReportTemplate="Mostrando del {first} al {last} de {totalRecords} Usuarios"
            filters={filters as DataTableFilterMeta}
            filterDisplay="menu"
            loading={loading}
            emptyMessage="No hay Usuarios..."
            header={header}
            responsiveLayout="stack"
          >
            <Column field="id" header="Id" hidden headerStyle={{ minWidth: '5rem' }}></Column>

            <Column field="imageUrl" header="Avatar" body={imagen} headerStyle={{ minWidth: '5rem' }}></Column>
            <Column field="login" header="Usuario" sortable body={login} headerStyle={{ minWidth: '5rem' }}></Column>
            <Column field="email" header="Correo" sortable body={email} headerStyle={{ minWidth: '15rem' }}></Column>
            <Column field="activated" header="Estado" sortable body={activado} headerStyle={{ minWidth: '5rem' }}></Column>
            <Column field="authorities" header="Perfiles" sortable body={roles} headerStyle={{ minWidth: '10rem' }}></Column>
            <Column field="createdDate" header="Fecha de Creación" sortable body={createdDate} headerStyle={{ minWidth: '15rem' }}></Column>

            <Column body={actionBodyTemplate} headerStyle={{ minWidth: '10rem' }}></Column>
          </DataTable>

          <Dialog visible={dialogNew} style={{ width: '500px' }} header="Usuario" modal className="p-fluid  " onHide={hideDialogNuevo}>
            <div>
              <Row className="justify-content-center">
                <Col md="12">
                  {loading ? (
                    <p>Cargando...</p>
                  ) : (
                    <ValidatedForm onSubmit={saveUser} defaultValues={usuario}>
                      {selectedUsuario.id ? (
                        <ValidatedField
                          type="text"
                          name="id"
                          required
                          hidden
                          label={translate('global.field.id')}
                          validate={{ required: true }}
                        />
                      ) : null}
                      <ValidatedField
                        type="text"
                        name="login"
                        label={translate('userManagement.login')}
                        validate={{
                          required: {
                            value: true,
                            message: translate('register.messages.validate.login.required'),
                          },
                          pattern: {
                            value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                            message: translate('register.messages.validate.login.pattern'),
                          },
                          minLength: {
                            value: 1,
                            message: translate('register.messages.validate.login.minlength'),
                          },
                          maxLength: {
                            value: 50,
                            message: translate('register.messages.validate.login.maxlength'),
                          },
                        }}
                      />
                      <ValidatedField type="select" name="langKey" label={translate('userManagement.langKey')}>
                        {locales.map(locale => (
                          <option value={locale} key={locale}>
                            {languages[locale].name}
                          </option>
                        ))}
                      </ValidatedField>
                      <ValidatedField
                        type="text"
                        name="firstName"
                        label={translate('userManagement.firstName')}
                        validate={{
                          maxLength: {
                            value: 50,
                            message: translate('entity.validation.maxlength', { max: 50 }),
                          },
                        }}
                      />
                      <ValidatedField
                        type="text"
                        name="lastName"
                        label={translate('userManagement.lastName')}
                        validate={{
                          maxLength: {
                            value: 50,
                            message: translate('entity.validation.maxlength', { max: 50 }),
                          },
                        }}
                      />
                      <ValidatedField
                        name="email"
                        label={translate('global.form.email.label')}
                        placeholder={translate('global.form.email.placeholder')}
                        type="email"
                        validate={{
                          required: {
                            value: true,
                            message: translate('global.messages.validate.email.required'),
                          },
                          minLength: {
                            value: 5,
                            message: translate('global.messages.validate.email.minlength'),
                          },
                          maxLength: {
                            value: 254,
                            message: translate('global.messages.validate.email.maxlength'),
                          },
                          validate: v => isEmail(v) || translate('global.messages.validate.email.invalid'),
                        }}
                      />
                      <ValidatedField
                        type="checkbox"
                        name="activated"
                        check
                        value={true}
                        disabled={!usuario?.id}
                        label={translate('userManagement.activated')}
                      />

                      <MultiSelect
                        value={selectedUserChip}
                        options={listRoles}
                        onChange={e => setselectedUserChip(e.value)}
                        optionLabel="name"
                        placeholder="Roles"
                        display="chip"
                        className=" mb-4"
                        itemTemplate={rolesTemplate}
                        selectedItemTemplate={selectedRolesTemplate}
                      />

                      <Button color="primary" type="submit" disabled={isInvalid || updating}>
                        <span className="m-auto pl-2">
                          <FontAwesomeIcon icon="save" />
                          &nbsp;
                          <Translate contentKey="entity.action.save">Save</Translate>
                        </span>
                      </Button>
                    </ValidatedForm>
                  )}
                </Col>
              </Row>
            </div>
          </Dialog>

          <Dialog
            visible={dialog}
            style={{ width: '500px' }}
            header="Usuario"
            modal
            className="p-fluid"
            footer={dialogFooter}
            onHide={hideDialog}
          >
            <div className="">
              <div className="flex justify-content-center ">
                <Avatar
                  image={`content/uploads/${selectedUsuario.imageUrl}`}
                  shape="circle"
                  className="p-avatar-xl justify-content-center"
                  size="xlarge"
                ></Avatar>
              </div>
              <div className="field mt-5">
                <span className="p-float-label">
                  <InputText id="name" name="name" value={selectedUsuario.login} autoFocus />
                  <label htmlFor="name">Usuario*</label>
                </span>
              </div>
              <div className="field mt-5">
                <span className="p-float-label">
                  <InputText id="name" name="name" value={selectedUsuario.firstName + ' ' + selectedUsuario.lastName} />
                  <label htmlFor="name">Nombre</label>
                </span>
              </div>

              <div className="field mt-5">
                <span className="p-float-label p-input-icon-right">
                  <i className="pi pi-envelope" />
                  <InputText id="email" name="email" value={selectedUsuario.email} />
                  <label htmlFor="email">Correo</label>
                </span>
              </div>
              {selectedUsuario?.activated ? (
                <Button label="Activado" className="p-button-success mt-3"></Button>
              ) : (
                <Button label="Desactivado" className="p-button-danger mt-3"></Button>
              )}
              <h5 className="text-center mt-3">Perfiles</h5>

              {selectedUsuario?.authorities
                ? selectedUsuario.authorities.map((authority, j) => (
                    <div key={`user-auth-${j}`}>
                      <Chip label={authority.replace('ROLE_', '')} className=" custom-chip mr-3 mt-1" />
                    </div>
                  ))
                : null}
            </div>
          </Dialog>

          <Dialog
            visible={deleteDialog}
            style={{ width: '450px' }}
            header="Confirmar"
            modal
            footer={deleteDialogFooter}
            onHide={hideDeleteDialog}
          >
            <div className="flex align-items-center justify-content-center">
              <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
              {setSelectedUsuario && (
                <span>
                  ¿Seguro que quiere eliminar el Usuario: <b>{selectedUsuario.login}</b>?
                </span>
              )}
            </div>
          </Dialog>
        </div>
      </div>
    </div>
  );
};
export default UserCrud;
