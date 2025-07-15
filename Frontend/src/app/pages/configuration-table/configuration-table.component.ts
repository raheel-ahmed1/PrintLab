import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CalculatorService } from 'src/app/services/calculator.service';

@Component({
  selector: 'app-configuration-table',
  templateUrl: './configuration-table.component.html',
  styleUrls: ['./configuration-table.component.css']
})
export class ConfigurationTableComponent implements OnInit {

  fields: any[] = [];
  press: any[] = [];
  selectedPress: any;
  margin: any;
  setupFee: any;
  cutting: any;
  cuttingImpression: any;
  bataDefault: any;
  filteredItems: any[] = [];
  isLoading: boolean = false;


  @Output() specifications = new EventEmitter<object>();
  @Input() selectedValue: any;
  constructor(private calculator: CalculatorService) { }
  ngOnInit(): void {
    this.fields = this.calculator.getFields();
    this.press = this.calculator.getPress();
  }
  getSelectedPressImpressions(): any {
    const selectedPressId = parseInt(this.selectedPress, 10);
    const selectedMachine = this.press.find(machine => machine.id === selectedPressId);
    return selectedMachine ? selectedMachine.impressions : NaN;
  }

  getSelectedPressBaseRate(): number {
    const impressions = parseFloat(this.getSelectedPressImpressions());
    return impressions;
  }
  getSelectedPressCtpRate(): any {
    const selectedPressId = parseInt(this.selectedPress, 10);
    const selectedMachine = this.press.find(machine => machine.id === selectedPressId);
    return selectedMachine ? selectedMachine.ctp : '';
  }


  getBataRate(selectedValue: string, selectedMachineId: any): any {
    const selectedPressId = parseInt(selectedMachineId, 10);
    const selectedMachine = this.press.find(machine => machine.id === selectedPressId);

    if (!selectedMachine) {
      return 'Press not found';
    }
    const key = `s${this.normalizeSizeValue(selectedValue)}`;
    if (selectedMachine.hasOwnProperty(key)) {
      return selectedMachine[key];
    }

    return 'Value not found';
  }

  normalizeSizeValue(selectedValue: string): string {
    return selectedValue.replace(/[^a-zA-Z0-9]/g, '').toLowerCase();
  }



  saveDataAndShowLoader() {
    this.saveData();
    setTimeout(() => {
      this.isLoading = false;
    }, 700);
  }

  saveData() {
      this.specifications.emit({
        margin: this.margin,
        setupFee: this.setupFee,
        press: this.selectedPress,
      });
      this.isLoading = true;
  }
}
