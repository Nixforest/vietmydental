//
//  G01F01S01VC.swift
//  dental
//
//  Created by SPJ on 2/16/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01F01S01VC: ChildExtViewController {
    // MARK: Properties
    /** Data */
    var _data:              MedicalRecordRespBean   = MedicalRecordRespBean()
    /** Customer id */
    var _id:                String                  = DomainConst.BLANK
    /** Information table view */
    var _tblInfo:           UITableView             = UITableView()
    /** Infor list */
    var _listInfo:          [ConfigurationModel]    = [ConfigurationModel]()
    /** Refrest control */
    lazy var refreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.addTarget(self, action: #selector(handleRefresh(_:)), for: .valueChanged)
        return refreshControl
    }()
    
    // MARK: Static values
    // MARK: Constant    
    
    // MARK: Override methods
    /**
     * Called after the controller's view is loaded into memory.
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.createNavigationBar(title: DomainConst.CONTENT00544)
        createInfoTableView()
        self.view.addSubview(_tblInfo)
        requestData()
    }
    
    /**
     * Notifies the view controller that its view was added to a view hierarchy.
     */
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        requestData()
    }
    
    /**
     * Set data
     */
    override func setData(_ notification: Notification) {
        let data = (notification.object as! String)
        let model = MedicalRecordRespBean(jsonString: data)
        if model.isSuccess() {
            _data = model
            _tblInfo.reloadData()
        } else {
            showAlert(message: model.message)
        }
    }
    
    // MARK: Logic
    /**
     * Request data
     */
    internal func requestData(action: Selector = #selector(setData(_:))) {
        MedicalRecordInfoRequest.request(
            action: action,
            view: self,
            id: _id)
    }
    
    /**
     * Reset data
     */
    private func resetData() {
    }
    
    /**
     * Handle refresh
     */
    internal func handleRefresh(_ sender: AnyObject) {
        self.resetData()
        requestData(action: #selector(finishHandleRefresh(_:)))
    }
    
    /**
     * Handle finish refresh
     */
    internal func finishHandleRefresh(_ notification: Notification) {
        setData(notification)
        refreshControl.endRefreshing()
    }
    
    /**
     * Set value of id
     * - parameter id: Customer id
     */
    public func setId(id: String) {
        self._id = id
    }
    
    /**
     * Get data from id
     * - parameter id:  Id of data
     * - returns:       Value of data
     */
    public func getData(id: String) -> String {
        for item in self._data.data {
            if item.id == id {
                return item.name
            }
        }
        return DomainConst.BLANK
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
        _tblInfo.addSubview(refreshControl)
    }
}

// MARK: Protocol - UITableViewDataSource
extension G01F01S01VC: UITableViewDataSource {
    /**
     * Tells the data source to return the number of rows in a given section of a table view.
     * - returns: List information count
     */
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self._data.data.count
    }
    
    /**
     * Asks the data source for a cell to insert in a particular location of the table view.
     */
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row > self._data.data.count {
            return UITableViewCell()
        }
        let data = self._data.data[indexPath.row]
        switch data.id {
        case DomainConst.ITEM_MEDICAL_HISTORY:
            let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "Cell")
            cell.textLabel?.text = data.name
            cell.textLabel?.font = GlobalConst.BASE_FONT
            cell.imageView?.image = ImageManager.getImage(named: DomainConst.INFORMATION_IMG_NAME, margin: GlobalConst.MARGIN_CELL_X)
            cell.imageView?.contentMode = .scaleAspectFit
            cell.accessoryType = .detailDisclosureButton
            return cell
        case DomainConst.ITEM_ADDRESS:
            let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "Cell")
            cell.textLabel?.text = data.name
            cell.textLabel?.font = GlobalConst.SMALL_FONT
            cell.textLabel?.lineBreakMode = .byWordWrapping
            cell.textLabel?.numberOfLines = 0
            cell.imageView?.image = ImageManager.getImage(named: DomainConst.INFORMATION_IMG_NAME, margin: GlobalConst.MARGIN_CELL_X)
            cell.imageView?.contentMode = .scaleAspectFit
            return cell
        default:
            let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "Cell")
            cell.textLabel?.text = data.name
            cell.textLabel?.font = GlobalConst.BASE_FONT
            cell.textLabel?.lineBreakMode = .byWordWrapping
            cell.textLabel?.numberOfLines = 0
            //        cell.detailTextLabel?.text = data._dataStr
            //        cell.detailTextLabel?.font = GlobalConst.SMALL_FONT
            //                cell.detailTextLabel?.lineBreakMode = .byWordWrapping
            //                cell.detailTextLabel?.numberOfLines = 0
            cell.imageView?.image = ImageManager.getImage(named: DomainConst.INFORMATION_IMG_NAME, margin: GlobalConst.MARGIN_CELL_X)
            cell.imageView?.contentMode = .scaleAspectFit
            return cell
        }
    }
}

// MARK: Protocol - UITableViewDelegate
extension G01F01S01VC: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row > self._data.data.count {
            return
        }
        let data = self._data.data[indexPath.row]
        switch data.id {
        case DomainConst.ITEM_MEDICAL_HISTORY:
            let view = G01F01S02VC(nibName: G01F01S02VC.theClassName,
                                   bundle: nil)
            view.setData(id: self._id, recordNumber: self.getData(id: DomainConst.ITEM_RECORD_NUMBER), data: data.data)            
            view.createNavigationBar(title: data.name)
            if let controller = BaseViewController.getCurrentViewController() {
                controller.navigationController?.pushViewController(view,
                                                                    animated: true)
            }
            break
        default: break
        }
    }
    
    /**
     * Asks the delegate for the height to use for a row in a specified location.
     */
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
//        if self._data.data[indexPath.row].id == DomainConst.ITEM_ADDRESS {
//            return GlobalConst.LABEL_H * 3
//        } else 
        if (self._data.data[indexPath.row].name.isEmpty) {
            return 0
        } else {
            return UITableViewAutomaticDimension
        }
    }
}
