import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { FileListComponent } from "./file-list/file-list.component";

const routes: Routes = [
  { path: 'files', component: FileListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class FilesystemRoutingModule {
}
