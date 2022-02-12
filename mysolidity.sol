pragma solidity ^0.5.1;

contract MyContract{

    uint public noOfmembersOfChitFund=0;
    mapping(uint => Person) public people;


    uint highestBid=0;
    uint256 public openingTime = 1642515260;// to be varied
    bool biddableStatus=true;
    address public bidWinner;
    address private owner;
    uint collectedMoney;
    uint numberOfPeopleWhoPaid=0;


    modifier onlyWhileBidding(){
        require(block.timestamp >= openingTime);
        
        _;
    }

    

    modifier onlyOwner(){
         require(msg.sender ==owner);
         _;
    }

    modifier onlyIfEligible(){
        require(biddableStatus==true);
        _;
    }

    modifier onlyMember(){
        require(!(msg.sender==owner));
        _;
    }

    modifier onlyIfEveryonePaid(){
        require(numberOfPeopleWhoPaid==noOfmembersOfChitFund);
        _;
    }

     constructor() public{
         owner=msg.sender;
         biddableStatus=true;
     }


    struct Person{
        uint id;
        string name;
    }

    struct Organiser{
        address owner ;
        
        string name;
    }

    function addPerson(string memory _name) public onlyWhileBidding onlyOwner {
        noOfmembersOfChitFund=noOfmembersOfChitFund+1;
        people[noOfmembersOfChitFund] = Person(noOfmembersOfChitFund, _name);
    
    }

    function paying(uint amount) onlyMember public{
        
        collectedMoney = collectedMoney+amount;
        emit Paid(msg.sender, amount);
    }

    

    function modifyOpeningTime(uint256 _openingTime) onlyOwner public {
        openingTime=_openingTime;
    }

    function bid(uint bidAmount) onlyMember onlyIfEveryonePaid onlyWhileBidding onlyIfEligible  public {
       if(bidAmount>highestBid && biddableStatus==true)
       {
           highestBid=bidAmount;
           bidWinner=msg.sender;
           biddableStatus=false;
       }
       

    }

    function endRound() onlyOwner  private{
        openingTime=openingTime+2592000;
        
    }


    
    event Paid(address payer, uint amount);
}