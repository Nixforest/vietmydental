//
//  G01F03S03VC.swift
//  dental
//
//  Created by SPJ on 2/20/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01F03S03VC: ChildExtViewController {
    // MARK: Properties
    /** Data */
    var _data:              ListConfigBean          = ListConfigBean()
    /** Treatment schedule id */
    var _scheduleId:         String                  = DomainConst.BLANK
    /** Information table view */
    var _tblInfo:           UITableView             = UITableView()
    /** Date */
    var _date:              Date                    = Date()
    
    // MARK: Static values
    // MARK: Constant
    
    // MARK: Override methods
    /**
     * Called after the controller's view is loaded into memory.
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        createNavigationBar(title: DomainConst.CONTENT00557)
        createRightNavigationItem(title: DomainConst.CONTENT00558,
                                  action: #selector(addNew(_:)),
                                  target: self)
        createInfoTableView()
        self.view.addSubview(_tblInfo)
    }
    
    /**
     * Notifies the view controller that its view was added to a view hierarchy.
     */
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        // Check if table view has selected item
        if let selectedIndex = _tblInfo.indexPathForSelectedRow, selectedIndex.section == 0 {
            // Get selected model
            let data = self._data.getData()[selectedIndex.row]
            switch data.id {
            case DomainConst.ITEM_DIAGNOSIS:
                let id = BaseModel.shared.sharedString
                if !id.isEmpty {
                    self._data.setData(id: DomainConst.ITEM_DIAGNOSIS_ID, value: id)
                    self._data.setData(id: DomainConst.ITEM_DIAGNOSIS,
                                       value: LoginBean.shared.getDiagnosisConfig(id: id))
                    _tblInfo.reloadData()
                }
            case DomainConst.ITEM_TEETH:
                let id = BaseModel.shared.sharedString
                if !id.isEmpty {
                    self._data.setData(id: DomainConst.ITEM_TEETH_ID, value: id)
                    self._data.setData(id: DomainConst.ITEM_TEETH,
                                       value: LoginBean.shared.getTeethConfig(id: id))
                    _tblInfo.reloadData()
                }
            case DomainConst.ITEM_TREATMENT:
                let id = BaseModel.shared.sharedString
                if !id.isEmpty {
                    self._data.setData(id: DomainConst.ITEM_TREATMENT_TYPE_ID, value: id)
                    self._data.setData(id: DomainConst.ITEM_TREATMENT,
                                       value: LoginBean.shared.getTreatmentConfig(id: id))
                    _tblInfo.reloadData()
                }
            default:
                break
            }
            BaseModel.shared.sharedString = DomainConst.BLANK
        }
    }
    
    /**
     * Set data
     */
    override func setData(_ notification: Notification) {
        let data = notification.object as! String
        let model = BaseRespModel(jsonString: data)
        if model.isSuccess() {
            BaseModel.shared.sharedString = data
            self.backButtonTapped(self)
        } else {
            showAlert(message: model.message)
        }
    }
    
    /**
     * Set data
     * - parameter bean: Data to set
     * - parameter scheduleId: Treatment schedule id
     */
    public func setData(bean: [ConfigExtBean], scheduleId: String) {
        self._data.setData(data: bean)
        self._scheduleId = scheduleId
    }
    
    public func resetData() {
        self._data.resetStrData()
    }
    
    internal func addNew(_ sender: AnyObject) {
        requestCreate()
    }
    
    internal func requestCreate(isShowLoading: Bool = true) {
        TreatmentScheduleDetailCreateRequest.request(
            action: #selector(setData(_:)),
            view: self,
            id: self._scheduleId,
            time: CommonProcess.getDateString(date: self._date, format: "yyyy/MM/dd hh:mm:ss"),
            teeth_id: self._data.getData(id: DomainConst.ITEM_TEETH_ID)._dataStr,
            diagnosis: self._data.getData(id: DomainConst.ITEM_DIAGNOSIS_ID)._dataStr,
            treatment: self._data.getData(id: DomainConst.ITEM_TREATMENT_TYPE_ID)._dataStr,
            isShowLoading: isShowLoading)
    }
    
    // MARK: Logic
    /**
     * Handle input date
     * - parameter id: Id of item
     */
    internal func inputDate(id: String) {
        var date = Date()
        // If date value is not empty
        if !self._data.getData(id: DomainConst.ITEM_START_DATE)._dataStr.isEmpty {
            date = self._date
        }
        self._date = date
        self._data.setData(id: DomainConst.ITEM_START_DATE,
                           value: date.dateString())
        self._tblInfo.reloadData()
        let alert = UIAlertController(style: .actionSheet,
                                      title: DomainConst.CONTENT00559)
        alert.addDatePicker(mode: .dateAndTime, date: date,
                            minimumDate: nil, maximumDate: nil,
                            action: {date in
                                self._date = date
                                self._data.setData(id: DomainConst.ITEM_START_DATE,
                                                   value: date.dateTimeString())
                                self._tblInfo.reloadData()
        })
        let ok = UIAlertAction(title: DomainConst.CONTENT00008, style: .cancel, handler: nil)
        alert.addAction(ok)
        self.present(alert, animated: true, completion: nil)
    }
    
    /**
     * Handle select teeth
     * - parameter id: id of item
     */
    public func inputTeeth(id: String) {
        let view = SelectionVC(nibName: SelectionVC.theClassName, bundle: nil)
        view.createNavigationBar(title: self._data.getData(id: DomainConst.ITEM_TEETH).name)
        view.setData(data: LoginBean.shared.teeth,
                     selectedValue: self._data.getData(
                        id: DomainConst.ITEM_TEETH_ID)._dataStr)
        self.push(view, animated: true)
    }
    
    /**
     * Handle select diagnosis
     */
    public func inputDiagnosis() {
        let view = G01F02S03VC(nibName: G01F02S03VC.theClassName, bundle: nil)
        view.createNavigationBar(title: self._data.getData(id: DomainConst.ITEM_DIAGNOSIS).name)
        view.setData(data: LoginBean.shared.diagnosis,
                     selectedValue: self._data.getData(
                        id: DomainConst.ITEM_DIAGNOSIS_ID)._dataStr)
        self.push(view, animated: true)
    }
    
    /**
     * Handle select treatment type
     */
    public func inputTreatment() {
        let view = G01F03S02VC(nibName: G01F03S02VC.theClassName, bundle: nil)
        view.createNavigationBar(title: self._data.getData(id: DomainConst.ITEM_TREATMENT).name)
        view.setDataExt(data: LoginBean.shared.treatment,
                        selectedValue: self._data.getData(id: DomainConst.ITEM_TREATMENT_TYPE_ID)._dataStr)
        self.push(view, animated: true)
    }
    
    // MARK: Layout
    
    // MARK: Information table view
    private func createInfoTableView() {
        _tblInfo.frame = CGRect(
            x: 0, y: 0,
            width: UIScreen.main.bounds.width,
            height: UIScreen.main.bounds.height)
        _tblInfo.dataSource = self
        _tblInfo.delegate = self
    }
}

// MARK: Protocol - UITableViewDataSource
extension G01F03S03VC: UITableViewDataSource {
    /**
     * Asks the data source to return the number of sections in the table view.
     * - returns: 1 section
     */
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    /**
     * Tells the data source to return the number of rows in a given section of a table view.
     * - returns: List information count
     */
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return self._data.count()
        } else {
            // For future
            return 0
        }
    }
    
    /**
     * Asks the data source for a cell to insert in a particular location of the table view.
     */
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch indexPath.section {
        case 0:
            if indexPath.row > self._data.count() {
                return UITableViewCell()
            }
            let data = self._data.getData()[indexPath.row]
            switch data.id {
            case DomainConst.ITEM_ID,
                 DomainConst.ITEM_END_DATE,
                 DomainConst.ITEM_DIAGNOSIS_ID, 
                 DomainConst.ITEM_TEETH_ID,
                 DomainConst.ITEM_TREATMENT_TYPE_ID, 
                 DomainConst.ITEM_NOTE,
                 DomainConst.ITEM_STATUS,
                 DomainConst.ITEM_TYPE,
                 DomainConst.ITEM_CAN_UPDATE,
                 DomainConst.ITEM_DETAILS:
                let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "Cell")
                cell.contentView.isHidden = true
                return cell
            default:
                let cell = UITableViewCell(style: .value1, reuseIdentifier: "Cell")
                cell.textLabel?.text = data.name
                cell.textLabel?.font = GlobalConst.BASE_FONT
                cell.detailTextLabel?.text = data._dataStr
                cell.detailTextLabel?.font = GlobalConst.BASE_FONT
                cell.detailTextLabel?.lineBreakMode = .byWordWrapping
                cell.detailTextLabel?.numberOfLines = 0
                if data._dataStr.isEmpty {
                    cell.detailTextLabel?.text = LoginBean.shared.getUpdateText()
                    cell.detailTextLabel?.textColor = UIColor.red
                }
                return cell
            }
        case 1:     // For future
            break
        default:
            break
        }
        
        return UITableViewCell()        
    }
}

// MARK: Protocol - UITableViewDelegate
extension G01F03S03VC: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.section {
        case 0:
            if indexPath.row > self._data.count() {
                return
            }
            let data = self._data.getData()[indexPath.row]
            switch data.id {
            case DomainConst.ITEM_START_DATE:
                self.inputDate(id: data.id)
            case DomainConst.ITEM_TEETH:
                inputTeeth(id: data.id)
            case DomainConst.ITEM_DIAGNOSIS:
                inputDiagnosis()
            case DomainConst.ITEM_TREATMENT:
                inputTreatment()
            case DomainConst.ITEM_CAN_UPDATE, DomainConst.ITEM_STATUS,
                 DomainConst.ITEM_TEETH_ID,
                 DomainConst.ITEM_ID, DomainConst.ITEM_DOCTOR:
                return
            default:
                break
            }
            break
        default:
            break
        }
        
    }
    
    /**
     * Asks the delegate for the height to use for a row in a specified location.
     */
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            switch self._data.getData()[indexPath.row].id {
            case DomainConst.ITEM_ID,
                 DomainConst.ITEM_END_DATE,
                 DomainConst.ITEM_DIAGNOSIS_ID, 
                 DomainConst.ITEM_TEETH_ID,
                 DomainConst.ITEM_TREATMENT_TYPE_ID, 
                 DomainConst.ITEM_NOTE,
                 DomainConst.ITEM_STATUS,
                 DomainConst.ITEM_TYPE,
                 DomainConst.ITEM_CAN_UPDATE,
                 DomainConst.ITEM_DETAILS:
                return 0
            default:
                return UITableViewAutomaticDimension
            }
        default:
            return UITableViewAutomaticDimension
        }
        
    }
}
