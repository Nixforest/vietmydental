//
//  G01F00S01VC.swift
//  dental
//
//  Created by SPJ on 2/2/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01F00S01VC: BaseParentViewController {
    // MARK: Properties
    @IBOutlet weak var searchBar: UISearchBar!
    /** Data */
    var _data:              CustomerListRespBean    = CustomerListRespBean()
    /** Information table view */
    @IBOutlet weak var _tblInfo: UITableView!
    //    var _tblInfo:           UITableView             = UITableView()
    /** Current page */
    var _page:              Int                     = 0
    /** Refrest control */
    lazy var refreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.addTarget(self, action: #selector(handleRefresh(_:)), for: .valueChanged)
        return refreshControl
    }()
    
    // MARK: Static values
    let MAXIMUM_HEIGHT_SEARCH_VIEW: CGFloat = 30.0
    let MINIMUM_HEIGHT_SEARCH_VIEW: CGFloat = 0.0
    // MARK: Constant
    
    // MARK: Override methods
    /**
     * Called after the controller's view is loaded into memory.
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        // Navigation
        self.createNavigationBar(title: DomainConst.CONTENT00541)
        createInfoTableView()
        requestData(date: CommonProcess.getDateString(date: Date(), format: DomainConst.DATE_TIME_FORMAT_2))
        searchBar.delegate = self
    }
    
    /**
     * Set data
     */
    override func setData(_ notification: Notification) {
        let data = (notification.object as! String)
        let model = CustomerListRespBean(jsonString: data)
        if model.isSuccess() {
            _data.updateData(bean: model.data)
            _tblInfo.reloadData()
            searchBar.alpha = 1
            let firstCellRect = CGRect(x: 0, y: 56, width: _tblInfo.frame.size.width, height: _tblInfo.frame.size.height)
            _tblInfo.scrollRectToVisible(firstCellRect, animated: false)
        } else {
            showAlert(message: model.message)
        }
    }
    
    // MARK: Logic
    /**
     * Request data
     * - add param strDate for function search by date
     */
    internal func requestData(action: Selector = #selector(setData(_:)), date: String) {
        CustomerListRequest.request(
            action: action,
            view: self,
            page: String(_page),
            date: date)
    }
    
    /**
     * Reset data
     */
    private func resetData() {
        _data.clearData()
        // Reset current search value
        self._page      = 0
        // Reload table
        _tblInfo.reloadData()
    }
    
    /**
     * Handle refresh
     */
    internal func handleRefresh(_ sender: AnyObject) {
        self.resetData()
        let strDate = ""
        requestData(action: #selector(finishHandleRefresh(_:)), date: strDate)
    }
    
    /**
     * Handle finish refresh
     */
    internal func finishHandleRefresh(_ notification: Notification) {
        setData(notification)
        refreshControl.endRefreshing()
    }
    
    /**
     * Handle input date
     */
    internal func inputDate() {
        let alert = UIAlertController(style: .actionSheet,
                                      title: DomainConst.CONTENT00559)
        alert.addDatePicker(mode: .date, date: Date(), minimumDate: nil, maximumDate: nil, action: { date in
            let str = CommonProcess.getDateString(date: date)
            self.searchBar.text = str
        })
        let ok = UIAlertAction(title: DomainConst.CONTENT00008, style: .default) { (action) in
            self.resetData()
            if let str = self.searchBar.text {
                if let d = CommonProcess.getDate(fromString: str, withFormat: "dd/MM/yyyy") {
                    self.requestData(date: CommonProcess.getDateString(date: d, format: DomainConst.DATE_TIME_FORMAT_2))
                }
            }
        }
        alert.addAction(ok)
        self.present(alert, animated: true, completion: nil)
    }
    // MARK: Layout
    
    // MARK: Information table view
    private func createInfoTableView() {
//        _tblInfo.frame = CGRect(
//            x: 0, y: 0,
//            width: UIScreen.main.bounds.width,
//            height: UIScreen.main.bounds.height)
        _tblInfo.dataSource = self
        _tblInfo.delegate = self
        _tblInfo.rowHeight = UITableViewAutomaticDimension
        _tblInfo.estimatedRowHeight = 150
        _tblInfo.addSubview(refreshControl)
        searchBar.alpha = 0
    }
}

extension G01F00S01VC: UISearchBarDelegate {
    /**
     *  show DatePicker to searchBar instead of keyboard
     */
    func searchBarShouldBeginEditing(_ searchBar: UISearchBar) -> Bool {
        inputDate()
        return false
    }
}

// MARK: Protocol - UITableViewDataSource
extension G01F00S01VC: UITableViewDataSource {
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
        return self._data.getList().count
    }
    
    /**
     * Asks the data source for a cell to insert in a particular location of the table view.
     */
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row > self._data.getList().count {
            return UITableViewCell()
        }
        let data = self._data.getList()[indexPath.row]
        let cell = UITableViewCell(style: .subtitle, reuseIdentifier: "Cell")
        cell.textLabel?.text = data.name
        cell.textLabel?.font = GlobalConst.BASE_BOLD_FONT
        cell.detailTextLabel?.text = data.address
        cell.detailTextLabel?.font = GlobalConst.SMALL_FONT
        cell.detailTextLabel?.lineBreakMode = .byWordWrapping
        cell.detailTextLabel?.numberOfLines = 0
        
        return cell
    }
    
    /**
     * Asks the delegate for the height to use for a row in a specified location.
     */
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return GlobalConst.LABEL_H * 3
    }
}

// MARK: Protocol - UITableViewDelegate
extension G01F00S01VC: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if self._data.getList().count > indexPath.row {
            let view = G01F00S02VC(nibName: G01F00S02VC.theClassName,
                                   bundle: nil)
            view.setId(id: self._data.getList()[indexPath.row].id)
            if let controller = BaseViewController.getCurrentViewController() {
                controller.navigationController?.pushViewController(view,
                                                                    animated: true)
            }
        }
        
//        let vc = G01F00S02CustomerInfoViewController()
//        vc.id = self._data.getList()[indexPath.row].id
//        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    /**
     * Tells the delegate the table view is about to draw a cell for a particular row.
     */
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        // Total page does not 1
        if _data.data.getTotalPage() != 1 {
            let lastElement = _data.getList().count - 1
            // Current is the last element
            if indexPath.row == lastElement {
                self._page += 1
                // Page less than total page
                if self._page <= _data.data.getTotalPage() {
                    let strDate = CommonProcess.getDateString(date: Date())
                    self.requestData(date: strDate)
                }
            }
        }
    }
}
