//
//  G01F00S02VC.swift
//  dental
//
//  Created by SPJ on 2/2/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01F00S02VC: ChildExtViewController {
    // MARK: Properties
    /** Data */
    var _data:              CustomerInfoRespBean    = CustomerInfoRespBean()
    /** Customer id */
    var _id:                String                  = DomainConst.BLANK
    /** Information table view */
    var _tblInfo:           UITableView             = UITableView()
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
        self.createNavigationBar(title: DomainConst.CONTENT00543)
        requestData()
    }
    
    /**
     * Set data
     */
    override func setData(_ notification: Notification) {
        let data = (notification.object as! String)
        let model = CustomerInfoRespBean(jsonString: data)
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
        CustomerInfoRequest.request(
            action: action,
            view: self,
            id: _id)
    }
    
    /**
     * Reset data
     */
    private func resetData() {
//        _data.clearData()
//        // Reset current search value
//        self._page      = 0
//        // Reload table
//        _tblInfo.reloadData()
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
extension G01F00S02VC: UITableViewDataSource {
    /**
     * Asks the data source to return the number of sections in the table view.
     * - returns: 1 section
     */
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    /**
     * Tells the data source to return the number of rows in a given section of a table view.
     * - returns: List information count
     */
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
//        return self._data.getList().count
        return 0
    }
    
    /**
     * Asks the data source for a cell to insert in a particular location of the table view.
     */
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
//        if indexPath.row > self._data.getList().count {
//            return UITableViewCell()
//        }
//        let data = self._data.getList()[indexPath.row]
//        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "Cell")
//        cell.textLabel?.text = data.name
//        cell.textLabel?.font = UIFont.systemFont(ofSize: UIFont.smallSystemFontSize)
//        cell.detailTextLabel?.text = data.address
//        cell.detailTextLabel?.font = GlobalConst.BASE_BOLD_FONT
//        cell.detailTextLabel?.lineBreakMode = .byWordWrapping
//        cell.detailTextLabel?.numberOfLines = 0
        
        return UITableViewCell()
    }
    
    /**
     * Asks the delegate for the height to use for a row in a specified location.
     */
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return GlobalConst.LABEL_H * 3
    }
}

// MARK: Protocol - UITableViewDelegate
extension G01F00S02VC: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    }
}
