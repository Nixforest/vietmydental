//
//  ListAgencyViewController.swift
//  dental
//
//  Created by Lâm Phạm on 8/1/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

protocol ListAgencyViewControllerDelegate: class {
    func ListAgencyViewControllerDidPick(listAgency: [Agent])
}

class ListAgencyViewController: ChildExtViewController {

    @IBOutlet weak var btnOK: UIButton!
    @IBOutlet weak var tbView: UITableView!
    
    var delegate: ListAgencyViewControllerDelegate!
    var listAgent: [Agent] = []
    var selectedAgents: [Agent] = []
    let cellID = "ListAgentTableViewCell"
    
    init(selectedAgents: [Agent]) {
        self.selectedAgents = selectedAgents
        super.init(nibName: "ListAgencyViewController", bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        createNavigationBar(title: "Danh sách chi nhánh")
        btnOK.drawRadius(6)
        tbView.delegate = self
        tbView.dataSource = self
        tbView.register(UINib(nibName: cellID, bundle: nil), forCellReuseIdentifier: cellID)
        fakeAgents()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func fakeAgents() {
        var agent = Agent(name: "Chi nhánh quận 1")
        agent.id = 1
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 2")
        agent.id = 2
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 3")
        agent.id = 3
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 4")
        agent.id = 4
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 5")
        agent.id = 5
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 6")
        agent.id = 6
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 7")
        agent.id = 7
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 8")
        agent.id = 8
        listAgent.append(agent)
        agent = Agent(name: "Chi nhánh quận 9")
        agent.id = 9
        listAgent.append(agent)
        for selected in selectedAgents {
            for agent in listAgent {
                if agent.id == selected.id {
                    agent.isSelected = true
                    break
                }
            }
        }
        tbView.reloadData()
    }
    
    @IBAction func btnOKAction(_ sender: Any) {
        var selectedAgents = [Agent]()
        for agent in listAgent {
            if agent.isSelected {
                selectedAgents.append(agent)
            }
        }
        delegate.ListAgencyViewControllerDidPick(listAgency: selectedAgents)
    }
    

}

extension ListAgencyViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listAgent.count
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: cellID, for: indexPath) as! ListAgentTableViewCell
        cell.selectionStyle = .none
        cell.loadAgent(listAgent[indexPath.row])
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        listAgent[indexPath.row].isSelected = !listAgent[indexPath.row].isSelected
        tbView.reloadRows(at: [indexPath], with: .none)
    }
}











