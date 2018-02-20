//
//  G01F02S03VC.swift
//  dental
//
//  Created by SPJ on 2/18/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01F02S03VC: SelectionVC {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func setData(data: [ConfigBean], selectedValue: String) {
        for item in data {
            self._data.append(item)
            if !item.data.isEmpty {
                for child in item.data {
                    self._data.append(child)
                }
            }
        }
        self._selectedValue = selectedValue
        self._originData = data
        tblInfo.reloadData()
    }
    
    /**
     * Asks the data source for a cell to insert in a particular location of the table view.
     */
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "Cell")
        if indexPath.row < self._data.count {
            let data = self._data[indexPath.row]
            let arrData = data.name.components(separatedBy: " - ")
            cell.textLabel?.text = arrData[0] + " - " + arrData[1]
            cell.textLabel?.font = GlobalConst.BASE_BOLD_FONT
            cell.detailTextLabel?.text = arrData[2]
            cell.detailTextLabel?.font = GlobalConst.BASE_FONT
//            cell.textLabel?.lineBreakMode = .byWordWrapping
//            cell.textLabel?.numberOfLines = 0
            if !_selectedArray.isEmpty {
                for item in _selectedArray {
                    if data.id == item.id {
                        cell.accessoryType = .checkmark
                        break
                    }
                }
            } else {            
                if data.id == _selectedValue {
                    cell.textLabel?.textColor = UIColor.red
                } else {
                    cell.textLabel?.textColor = UIColor.black
                }
            }
        }
        return cell
    }
}
