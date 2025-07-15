import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SettingsService } from 'src/app/services/settings.service';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent {

  tableData: boolean = true
  settingsArray: any = []
  search:string=''
  visible!: boolean
  error: string = ''

  constructor(private settingsService: SettingsService, private router: Router,private messageService: MessageService) { }

  ngOnInit(): void {
    this.getSettings()
  }

  getSettings() {
    this.settingsService.getSettings().subscribe(res => {
      this.settingsArray = res
      this.settingsArray.length == 0 ? this.tableData = true : this.tableData = false
    }, error => {
      this.error = error.error.error
      this.visible = true
    })
  }

  editSettings(id: any) {
    this.router.navigate(['/addSettings'], { queryParams: { id: id } });
  }

  deleteSettings(id: any) {
    this.settingsService.deleteSettings(id).subscribe(() => {
      this.getSettings()
    }, error => {
      this.error = error.error.error
      this.visible = true
    })
  }

  searchSettings(key: any) {
    if (this.search=='') {
      this.getSettings()
    }else{
    this.settingsService.searchSettings(key.value).subscribe(res => {
      this.settingsArray = res
      this.settingsArray.length == 0 ? this.tableData = true : this.tableData = false;
    }, error => {
      this.showError(error);
      this.visible = true
    })
  }
  }
  showError(error:any) {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.error }); 
  }
}
