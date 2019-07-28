import { Routes } from '@angular/router';

// import { movieMgmtRoute, moviePopupRoute } from './';
import { AdminLayoutComponent } from '@app/layouts';

// const ADMIN_ROUTES = [...movieMgmtRoute];

export const adminState: Routes = [
    {
        path: '',
        canActivate: [],
        // component: AdminLayoutComponent,
        // children: ADMIN_ROUTES
    }
];
