
import {MatFormFieldControl, MatFormFieldModule, MatOptionModule, MatSelectModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgModule} from "@angular/core";
import {AdminLogComponent} from "./admin-log.component";

@NgModule({
  declarations: [
    AdminLogComponent
  ],
  imports: [
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
  ],
})
export class AdminLogModule {}
